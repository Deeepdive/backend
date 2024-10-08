package deepdive.backend.auth.service;

import deepdive.backend.auth.domain.OAuth2Attribute;
import deepdive.backend.auth.domain.UserProfile;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public UserProfile loadUser(OAuth2UserRequest userRequest)
        throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        log.info("loadUser 실행합니다.");
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("attributes = {}", attributes);

        OAuth2Attribute oAuth2Attribute =
            OAuth2Attribute.of(provider, userNameAttributeName, attributes);

        log.info("provider = {}", provider);
        log.info("oauth2AttributeInfo = {}", oAuth2Attribute);

        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();

        // TODO : 추후 admin 계정 추가 시 ROLE 부분 리팩
        return new UserProfile(provider,
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), memberAttribute);
    }
}
