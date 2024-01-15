package deepdive.backend.auth.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PROTECTED)
@Getter
public class OAuth2Attribute {

    private Map<String, Object> attributes; // Oauth를 통해 로그인한 유저의 상세정보
    private String attributeKey; // sub, id 등등.. -> 어디 oauth로 로그인했는지
    private String email; // 입력한 유저의 email
    private String name;
    private String picture; // 프로필 사진
    private String provider; // 발급 기관 -> google, naver, kakao ...

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

        return OAuth2Attribute.builder()
            .email((String) attributes.get("email"))
            .provider(provider)
            .attributes(attributes)
            .attributeKey(attributeKey)
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

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
            .email((String) response.get("email"))
            .attributes(response)
            .provider(provider)
            .attributeKey(attributeKey)
            .build();
    }

    public Map<String, Object> convertToMap() {
        // 새로운 attribute를 만들어냅니다.
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("picture", picture);
        map.put("email", email);
        map.put("provider", provider);

        return map;
    }
}
