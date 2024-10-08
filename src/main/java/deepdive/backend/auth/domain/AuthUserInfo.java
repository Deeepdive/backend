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
    private String email;
    private Long memberId;

    public static AuthUserInfo of() {
        return (AuthUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
