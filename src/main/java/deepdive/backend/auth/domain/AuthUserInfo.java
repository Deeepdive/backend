package deepdive.backend.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class AuthUserInfo {

    private String oauthId;
    private Long memberId;
    private String email;
    private String picture;

    public static AuthUserInfo of() {
        return (AuthUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
