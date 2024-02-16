package deepdive.backend.dto.member;

import jakarta.validation.constraints.Email;

public record MemberRegisterRequestDto(
    @Email(message = "이메일 형식으로 적어주세요")
    String email,
    String os,
    Boolean isAlarm,
    Boolean isMarketing
) {

}
