package deepdive.backend.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberRegisterRequestDto(
    @Email(message = "이메일 형식으로 적어주세요")
    String email,
    @NotNull
    String provider,
    @NotNull
    String oauthId,
    Boolean isMarketing
) {

}
