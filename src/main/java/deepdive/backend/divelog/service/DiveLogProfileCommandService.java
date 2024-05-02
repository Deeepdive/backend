package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.divelog.repository.DiveLogProfileRepository;
import deepdive.backend.profile.domain.entity.Profile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiveLogProfileCommandService {

	private final DiveLogProfileRepository diveLogProfileRepository;

	public void saveBuddiesProfile(DiveLog diveLog, List<Profile> buddies) {
		List<DiveLogProfile> buddiesProfile = buddies.stream()
			.map(profile -> DiveLogProfile.of(diveLog, profile))
			.toList();
		diveLogProfileRepository.saveAll(buddiesProfile);
	}
}
