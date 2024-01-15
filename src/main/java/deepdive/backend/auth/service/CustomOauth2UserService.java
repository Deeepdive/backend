package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.Member;
import deepdive.backend.auth.domain.OAuth2Attribute;
import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.auth.jwt.service.MemberService;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    @Override
    public UserProfile loadUser(OAuth2UserRequest userRequest)
        throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Attribute oAuth2Attribute =
            OAuth2Attribute.of(provider, userNameAttributeName, attributes);
        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();
        String email = (String) memberAttribute.get("email");

        Optional<Member> memberInDb = memberService.findByEmail(email);
        if (memberInDb.isEmpty()) { // 처음 가입한 회원이라면
            memberAttribute.put("exist", false);
            return new UserProfile(provider,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), memberAttribute);
        }

        // 이미 등록된 유저
        memberAttribute.put("exist", true);
        return new UserProfile(provider,
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), memberAttribute);
    }
}
