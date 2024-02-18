package deepdive.backend.dto.profile;

/**
 * 최초 프로필 업데이트 등록에 대한 RequestBody
 *
 * @param nickName
 * @param pictureNumber
 * @param certOrganization
 * @param certType
 * @param isTeacher
 */
public record ProfileRequestDto(
    String nickName,
    Integer pictureNumber,
    String certOrganization,
    String certType,
    Boolean isTeacher,
    String etc
) {

}