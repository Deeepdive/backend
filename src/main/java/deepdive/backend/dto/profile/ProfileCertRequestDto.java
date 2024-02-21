package deepdive.backend.dto.profile;

import jakarta.validation.constraints.NotEmpty;

/**
 * 자격증 관련 업데이트 RequestBody
 *
 * @param certOrganization
 * @param certType
 * @param isTeacher
 */
public record ProfileCertRequestDto(
    @NotEmpty
    String certOrganization,
    String certType,
    Boolean isTeacher,
    String etc) {

}
