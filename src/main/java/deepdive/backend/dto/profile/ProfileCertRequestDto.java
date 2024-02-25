package deepdive.backend.dto.profile;

import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;

/**
 * 자격증 관련 업데이트 RequestBody
 *
 * @param certOrganization
 * @param certType
 * @param isTeacher
 */
public record ProfileCertRequestDto(
	CertOrganization certOrganization,
	CertType certType,
	Boolean isTeacher,
	String etc
) {

}
