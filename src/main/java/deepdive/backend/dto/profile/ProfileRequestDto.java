package deepdive.backend.dto.profile;

/**
 * 최초 프로필 업데이트 등록에 대한 RequestBody
 *
 * @param nickName
 * @param picture
 * @param certOrganization
 * @param certType
 * @param isTeacher
 */
public record ProfileRequestDto(
    String nickName,
    String picture,
    String certOrganization,
    String certType,
    Boolean isTeacher,
    String etc
) {

}