package deepdive.backend.dto.profile;

import jakarta.validation.constraints.NotEmpty;

public record ProfileDefaultRequestDto(@NotEmpty String nickName,
                                       Integer urlNumber) {

}
