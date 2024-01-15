package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.Member;
import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.auth.jwt.service.JwtService;
import deepdive.backend.auth.jwt.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String oauthEmail = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        boolean isExistMember = oAuth2User.getAttribute("exist");
        String role = oAuth2User.getAuthorities().stream().findFirst()
            .orElseThrow(IllegalArgumentException::new).getAuthority();

        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        String email = (String) userProfile.getAttributes().get("email");

        Member member = memberService.findByEmail(email)
            .orElseGet(() -> memberService.generateMemberByUserProfile(userProfile));

        log.info("JWT access 토큰 발행 시작");
        String accessToken = jwtService.createAccessToken(member.getId(), member.getEmail());
        if (isExistMember) {
            jwtService.updateRefreshToken(member.getId(), member.getEmail());
        }

        getRedirectStrategy()
            .sendRedirect(
                request,
                response,
                UriComponentsBuilder
                    .fromUriString("http://localhost:3000")
                    .queryParam("token", accessToken)
                    .queryParam("email", email)
                    .build()
                    .toUriString()
            );
    }
}
