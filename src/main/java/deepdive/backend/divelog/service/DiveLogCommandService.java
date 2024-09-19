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
	public void updateImageUrl(List<String> imageUrls, DiveLog diveLog) {
		List<DiveLogImage> diveLogImages = imageUrls.stream()
				.map(DiveLogImage::of)
				.toList();

		diveLog.getImage().addAll(diveLogImages);
	}

	@Transactional
	public void saveImage(String url) {
		diveLogImageRepository.save(DiveLogImage.of(url));
	}


	@Transactional
	public void deleteByUser(Long memberId, Long diveLogId) {
		DiveLog diveLog = diveLogRepository.findOneByMemberId(memberId, diveLogId).orElseThrow(
				ExceptionStatus.NOT_FOUND_LOG::asServiceException);
		diveLogRepository.delete(diveLog);
	}

	@Transactional
	public void deleteImage(Long diveLogId) {
		diveLogImageRepository.findByDiveLogId(diveLogId)
				.forEach(DiveLogImage::delete);
	}
}
