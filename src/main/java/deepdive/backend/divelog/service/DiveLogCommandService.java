package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.domain.entity.DiveLogImage;
import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.divelog.repository.DiveLogImageRepository;
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
	private final DiveLogImageRepository diveLogImageRepository;

	@Transactional
	public List<DiveLogProfile> createBuddiesProfiles(DiveLog diveLog,
			List<Profile> newBuddyProfiles) {

		return newBuddyProfiles.stream()
				.map(profile -> DiveLogProfile.of(diveLog, profile))
				.toList();
	}

	@Transactional
	public void saveImage(List<String> imageUrls, Long diveLogId) {
		imageUrls.stream()
				.map(imageUrl -> DiveLogImage.of(imageUrl, diveLogId))
				.forEach(diveLogImageRepository::save);
	}


	@Transactional
	public void deleteByUser(Long memberId, Long diveLogId) {
		DiveLog diveLog = diveLogRepository.findOneByMemberId(memberId, diveLogId).orElseThrow(
				ExceptionStatus.NOT_FOUND_LOG::asServiceException);
		diveLogRepository.delete(diveLog);
	}

	@Transactional
	public void deleteImageByDiveLogId(Long diveLogId) {
		diveLogImageRepository.deleteByDiveLogId(diveLogId);
	}
}
