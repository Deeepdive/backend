package deepdive.backend.dto.profile;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;

public record ProfileDefaultRequestDto(
	@NotEmpty String nickName,
	@Nullable
	Integer urlNumber) {

}
