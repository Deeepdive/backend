package deepdive.backend.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Builder
@AllArgsConstructor
public class AuthUserInfo {

    private String oauthId;
    private String email;
    private String profile;

    public static AuthUserInfo of() {
        return (AuthUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
