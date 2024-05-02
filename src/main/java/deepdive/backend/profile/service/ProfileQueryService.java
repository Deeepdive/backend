package deepdive.backend.profile.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileQueryService {

	private final ProfileRepository profileRepository;

	public List<Profile> getProfiles(List<Long> ids) {
		return profileRepository.findAllById(ids);
	}

	public List<String> getProfileNames(List<Profile> profiles) {
		return profileRepository.findNickNames(profiles);
	}

	public Profile getByNickName(String nickName) {
		return profileRepository.findByNickName(nickName)
			.orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
	}

	public Profile getByMemberId() {
		Long memberId = AuthUserInfo.of().getMemberId();
		log.info("member id = {}", memberId);

		return profileRepository.findByMemberId(memberId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
	}

	public Profile getByMemberId(Long memberId) {
		return profileRepository.findByMemberId(memberId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
	}
}
