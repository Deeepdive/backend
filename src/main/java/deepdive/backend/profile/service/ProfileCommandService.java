package deepdive.backend.profile.service;

import deepdive.backend.profile.domain.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileCommandService {

	public void updateDefaultProfile(Profile profile, String url, String nickName) {
		profile.updateDefaultProfile(url, nickName);
	}
}
