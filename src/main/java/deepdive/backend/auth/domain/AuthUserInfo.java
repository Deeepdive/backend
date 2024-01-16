package deepdive.backend.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthUserInfo {

    private String oauthId;
    private String email;
    private String profile;
}
