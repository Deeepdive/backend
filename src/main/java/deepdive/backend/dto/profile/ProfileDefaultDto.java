package deepdive.backend.dto.profile;

/**
 * 기본 프로필에 대한 Request, Response DTO
 *
 * @param nickName
 * @param picture
 */
public record ProfileDefaultDto(String nickName, String picture) {

}
