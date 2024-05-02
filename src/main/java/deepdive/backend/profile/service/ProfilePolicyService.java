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
		verifyNoneCertificationType(organization, type);
		verifyCommonCertType(organization, type);
	}

	private void verifyNoneCertificationType(CertOrganization organization, CertType type) {
		if (organization.equals(CertOrganization.NONE)) {
			if (!type.equals(CertType.NONE)) {
				throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
			}
		}
	}


	public void verifyCommonCertType(CertOrganization organization, CertType type) {
		if (!(CertOrganization.common(organization) && CertType.common(type))) {
			throw ExceptionStatus.INVALID_CERT_TYPE.asServiceException();
		}
	}

	public void verifyNickName(String oldNickName, String newNickName) {
		if (isNewNickName(oldNickName, newNickName) && isExistingNickName(newNickName)) {
			throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
		}
	}

	private boolean isExistingNickName(String nickName) {
		log.warn("이미 존재하는 닉네임인지 검증 수행");
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
