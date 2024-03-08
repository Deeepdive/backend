package deepdive.backend.dto.member;

import deepdive.backend.member.domain.Provider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberRegisterRequestDto(
	@Email(message = "이메일 형식으로 적어주세요")
	String email,
	@NotNull
	Provider provider,
	@NotNull
	String oauthId,
	@NotNull
	Boolean isMarketing
) {

}
