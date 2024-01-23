package deepdive.backend.dto.profile;

/**
 * 기본 프로필 정보에 대한 Response
 *
 * @param nickName
 * @param picture
 */
public record ProfileResponseDto(String nickName, String picture) {

}
