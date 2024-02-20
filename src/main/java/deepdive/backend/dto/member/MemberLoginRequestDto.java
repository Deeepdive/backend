package deepdive.backend.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberLoginRequestDto(
    @NotNull
    String oauthId,
    @Email
    String email
) {

}
