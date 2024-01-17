package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.entity.Member;
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

        /**
         * 이게 맞나..
         * 동의 한 애들 -> db 내에 존재한다 -> updateRefreshToken, accessToken을 가지고 프론트에게 보내기.
         * 동의 안한 애들 -> db 내에 존재하지 않는다
         *            -> accessToken, registed T/F 프론트에게 보내기 -> 분기 통합,
         *            -> 프론트는 토큰을 갖고 다시 특정 url(/register)로 POST
         *            -> 멤버 최초 등록, DTO를 통해 isAlarm, isMarketing T/F값 업데이트
         */

        boolean isRegistered = false;

        if (member.getIsAgree()) {
            isRegistered = true;
            log.info("member entity = {}", member);
            // 만일 유저가 로그인한 시점이 refreshToken이 만료될 시점보다 1일 이전이라면?
            jwtService.updateRefreshToken(member.getOauthId(), member.getEmail());
        }

        log.info("JWT access 토큰 발행 시작");
        // oauthId를 가지고 accessToken을 발행합니다.
        String accessToken = jwtService.createAccessToken(oauthId, email);

        getRedirectStrategy()
            .sendRedirect(
                request,
                response,
                UriComponentsBuilder
                    .fromUriString("http://localhost:3000")
                    .queryParam("token", accessToken)
                    .queryParam("isRegistered", isRegistered)
                    .build()
                    .toUriString()
            );

    }
}
