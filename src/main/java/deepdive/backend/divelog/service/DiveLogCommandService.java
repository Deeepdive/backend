package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.divelog.repository.DiveLogProfileRepository;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.entity.Profile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiveLogCommandService {

	private final DiveLogRepository diveLogRepository;
	private final DiveLogProfileRepository diveLogProfileRepository;

	@Transactional
	public void updateBuddiesProfiles(DiveLog diveLog, List<Profile> newBuddyProfiles) {
		List<DiveLogProfile> oldBuddyProfiles = diveLog.getProfiles();
		diveLogProfileRepository.deleteAll(oldBuddyProfiles);

		List<DiveLogProfile> diveLogProfiles = newBuddyProfiles.stream()
			.map(profile -> DiveLogProfile.of(diveLog, profile))
			.toList();
		diveLog.setProfiles(diveLogProfiles);
	}

	@Transactional
	public void saveBuddiesProfile(DiveLog diveLog, List<Profile> buddies) {
		List<DiveLogProfile> buddiesProfile = buddies.stream()
			.map(profile -> DiveLogProfile.of(diveLog, profile))
			.toList();
		diveLog.setProfiles(buddiesProfile);
	}

	@Transactional
	public void deleteByUser(Long memberId, Long diveLogId) {
		DiveLog diveLog = diveLogRepository.findOneByMemberId(memberId, diveLogId).orElseThrow(
			ExceptionStatus.NOT_FOUND_LOG::asServiceException);
		diveLogRepository.delete(diveLog);
	}
}
