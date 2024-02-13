package deepdive.backend.dto.profile;

import jakarta.validation.constraints.NotEmpty;

/**
 * 기본 프로필에 대한 Request, Response DTO
 *
 * @param nickName
 * @param picture
 */
public record ProfileDefaultDto(@NotEmpty String nickName, @NotEmpty String picture) {

}
