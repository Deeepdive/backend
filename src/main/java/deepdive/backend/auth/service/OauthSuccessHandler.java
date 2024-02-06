package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.jwt.service.JwtProvider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

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

        generateResponse(response, accessToken, isRegistered);
        if (isRegistered) {
            tokenProvider.updateRefreshToken(oauthId, email);
        }
    }

    private void generateResponse(HttpServletResponse response, String accessToken,
        boolean isRegistered) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        if (isRegistered) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        writer.print("{\"accessToken\":\"" + accessToken + "\"}");
        writer.flush();
    }

}
