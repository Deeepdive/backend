package deepdive.backend.profile.service;

import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileCommandService {

	private final ProfileRepository profileRepository;

	public void updateDefaultProfile(Profile profile, String url, String nickName) {
		profile.updateDefaultProfile(url, nickName);
	}

	public void deleteProfile(Profile profile) {
		profileRepository.delete(profile);
	}
}
