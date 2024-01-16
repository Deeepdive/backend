package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.Member;
import deepdive.backend.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    private final MemberService memberService;
    private final JwtService jwtService;

    /**
     * authentication에 성공한 유저들을 db에 저장합니다.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during the
     *                       authentication process.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        String email = userProfile.getAttributeByKey("email");
        String oauthId = userProfile.getAttributeByKey("id");

        Member member = memberService.registerMember(oauthId, email, userProfile);
        log.info("member entity = {}", member);

        log.info("JWT access 토큰 발행 시작");
        String accessToken = jwtService.createAccessToken(member.getOauthId(), member.getEmail());

        getRedirectStrategy()
            .sendRedirect(
                request,
                response,
                UriComponentsBuilder
                    .fromUriString("http://localhost:3000")
                    .queryParam("token", accessToken)
                    .build()
                    .toUriString()
            );
    }
}
