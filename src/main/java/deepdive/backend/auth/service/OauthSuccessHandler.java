package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.jwt.service.JwtProvider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String TARGET_URL = "http://localhost:3000";
    private final MemberService memberService;
    private final JwtProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        String email = userProfile.getAttributeByKey("email");
        String oauthId = userProfile.getAttributeByKey("id");
        String provider = userProfile.getAttributeByKey("provider");

        Optional<Member> member = memberService.findByEmail(email);
        boolean isRegistered = member.isPresent();

        log.info("JWT access 토큰 발행 시작");
        String accessToken = tokenProvider.createAccessToken(oauthId, email);
        if (isRegistered) {
            tokenProvider.updateRefreshToken(oauthId, email);
        }
        String redirectUrl = getRedirectUrl(isRegistered, accessToken, provider);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String getRedirectUrl(boolean isMemberPresent, String token, String provider) {
        String path = isMemberPresent ? TARGET_URL : TARGET_URL + "/agreement";

        return UriComponentsBuilder
            .fromUriString(path)
            .queryParam("token", token)
            .queryParam("provider", provider)
            .build()
            .toUriString();
    }
}
