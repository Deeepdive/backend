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
	public List<DiveLogProfile> createBuddiesProfiles(DiveLog diveLog,
		List<Profile> newBuddyProfiles) {

		return newBuddyProfiles.stream()
			.map(profile -> DiveLogProfile.of(diveLog, profile))
			.toList();
	}


	@Transactional
	public void deleteByUser(Long memberId, Long diveLogId) {
		DiveLog diveLog = diveLogRepository.findOneByMemberId(memberId, diveLogId).orElseThrow(
			ExceptionStatus.NOT_FOUND_LOG::asServiceException);
		diveLogRepository.delete(diveLog);
	}
}
