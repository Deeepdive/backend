package deepdive.backend.profile.service;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfilePolicyService {

	private final ProfileRepository profileRepository;

	public void verifyMatchCertProfile(CertOrganization organization, CertType type) {
		if (organization.equals(CertOrganization.NONE)) {
			if (!isValidNoneCert(organization, type)) {
				throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
			}
		}
		if (!isValidCommonCertMatch(organization, type)) {
			throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
		}
	}

	public boolean isValidCommonCertMatch(CertOrganization organization, CertType type) {
		return CertOrganization.common(organization) && CertType.common(type);
	}

	public boolean isValidNoneCert(CertOrganization organization, CertType certType) {
		return organization.equals(CertOrganization.NONE) && certType.equals(CertType.NONE);
	}

	public void verifyNickName(String oldNickName, String newNickName) {
		if (isNewNickName(oldNickName, newNickName) && isExistingNickName(newNickName)) {
			throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
		}
	}

	public boolean isExistingNickName(String nickName) {
		return profileRepository.findByNickName(nickName).isPresent();
	}

	private boolean isNewNickName(String oldNickName, String newNickName) {
		return !newNickName.equals(oldNickName);
	}

	public void verifySelfProfile(Profile self, Profile other) {
		if (self.equals(other)) {
			throw ExceptionStatus.INVALID_BUDDY_PROFILE.asServiceException();
		}
	}
}
