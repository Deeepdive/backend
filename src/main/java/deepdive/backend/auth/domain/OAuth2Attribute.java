package deepdive.backend.auth.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Builder(access = AccessLevel.PROTECTED)
@Getter
public class OAuth2Attribute {

    private Map<String, Object> attributes; // Oauth를 통해 로그인한 유저의 상세정보
    private String attributeKey; // sub, id 등등.. -> 어디 oauth로 로그인했는지 고유 번호
    private String email; // 입력한 유저의 email
    private String name;
    private String picture; // 프로필 사진
    private String provider; // 발급 기관 -> google, naver, kakao ...
    private String locale;
    private String attributeValue;

    public static OAuth2Attribute of(String provider, String attributeKey,
        Map<String, Object> attributes) {
        if (provider.equals("google")) {
            return ofGoogle(provider, attributeKey, attributes);
        }
        if (provider.equals("kakao")) {
            return ofKakao(provider, attributeKey, attributes);
        }
        if (provider.equals("naver")) {
            return ofNaver(provider, attributeKey, attributes);
        }
        throw new RuntimeException("잘못된 provider 입니다.");
    }

    private static OAuth2Attribute ofGoogle(String provider, String attributeKey,
        Map<String, Object> attributes) {

        // 필요 정보 -> sub 뒤에 숫자 어쩌고저쩌고, name, picture, email, locale

        return OAuth2Attribute.builder()
            .email((String) attributes.get("email"))
            .name((String) attributes.get("name"))
            .picture((String) attributes.get("picture"))
            .locale((String) attributes.get("locale"))
            .attributeKey(attributeKey)
            .provider(provider)
            .attributes(attributes)
            .build();
    }

    private static OAuth2Attribute ofKakao(String provider, String attributeKey,
        Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
            .email((String) kakaoAccount.get("email"))
            .provider(provider)
            .attributes(kakaoAccount)
            .attributeKey(attributeKey)
            .build();
    }

    private static OAuth2Attribute ofNaver(String provider, String attributeKey,
        Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get(attributeKey);

        log.info("attributeKey = {}", attributeKey);
        return OAuth2Attribute.builder()
            .email((String) response.get("email"))
            .attributes(response)
            .provider(provider)
            .attributeKey("id")
            .build();
    }

    public Map<String, Object> convertToMap() {
        // 새로운 attribute를 만들어냅니다. 각 oauth별로 공통된 정보중, 필요한 값만 다시 파싱
        Map<String, Object> map = new HashMap<>(attributes);
        map.put("id", attributes.get(attributeKey));
        map.put("picture", picture);
        map.put("locale", locale);
        map.put("email", email);
        map.put("provider", provider);

        return map;
    }
}
