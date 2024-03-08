package deepdive.backend.profile.service;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilePolicyService {

	public void verifyMatchCertProfile(CertOrganization organization, CertType type) {
		if (!(isValidCommonCertMatch(organization, type) || isValidNoneCert(organization, type))) {
			throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
		}
	}

	public boolean isValidCommonCertMatch(CertOrganization organization, CertType type) {
		return CertOrganization.common(organization) && CertType.common(type);
	}

	public boolean isValidNoneCert(CertOrganization organization, CertType certType) {
		return organization.equals(CertOrganization.NONE) && certType.equals(CertType.NONE);
	}
}
