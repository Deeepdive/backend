package deepdive.backend.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.dto.token.TokenInfo;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.Provider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberCommandService;
import deepdive.backend.member.service.MemberQueryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final MemberQueryService memberQueryService;
	private final MemberCommandService memberCommandService;
	private final JwtService jwtService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		UserProfile userProfile = (UserProfile) authentication.getPrincipal();
		String email = userProfile.getAttributeByKey("email");
		String oauthId = userProfile.getAttributeByKey("id");
		String providerInUserProfile = userProfile.getAttributeByKey("provider");
		Provider provider = Provider.of(providerInUserProfile);
		log.info("{}로 로그인을 시작합니다, email = {}", provider.name(), email);

		Member member = memberQueryService.findByEmail(email)
			.orElseGet(() -> memberCommandService.saveByOauthInfo(email, provider, oauthId));

		// 이메일은 같지만, provider가 다른 경우
		validateDuplicateEmailRegister(provider, member);
		boolean isRegistered = member.getIsMarketingAgree() != null;

		log.info("JWT 발행 시작");
		TokenInfo tokenInfo = jwtService.generateToken(member.getId(), oauthId);
		generateResponse(response, tokenInfo, isRegistered);
	}

	private void validateDuplicateEmailRegister(Provider provider, Member member) {
		Provider memberProvider = member.getProvider();
		if (!provider.equals(memberProvider)) {
			if (memberProvider.equals(Provider.GOOGLE)) {
				throw ExceptionStatus.DUPLICATE_GOOGLE.asServiceException();
			}
			if (memberProvider.equals(Provider.NAVER)) {
				throw ExceptionStatus.DUPLICATE_NAVER.asServiceException();
			}
			if (memberProvider.equals(Provider.KAKAO)) {
				throw ExceptionStatus.DUPLICATE_KAKAO.asServiceException();
			}
			if (memberProvider.equals(Provider.APPLE)) {
				throw ExceptionStatus.DUPLICATE_APPLE.asDomainException();
			}
		}
	}

	private void generateResponse(
		HttpServletResponse response,
		TokenInfo tokenInfo,
		boolean isRegistered) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		if (isRegistered) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		String tokenJsonInfo = objectMapper.writeValueAsString(tokenInfo);

		PrintWriter writer = response.getWriter();
		writer.print(tokenJsonInfo);
		writer.flush();
	}

}
