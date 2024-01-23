package deepdive.backend.dto.profile;

/**
 * 자격증 관련 업데이트 RequestBody
 *
 * @param certOrganization
 * @param certType
 * @param isTeacher
 */
public record ProfileCertRequestDto(String certOrganization, String certType, Boolean isTeacher) {

}
