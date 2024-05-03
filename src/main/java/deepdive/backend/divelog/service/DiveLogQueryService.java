package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.exception.ExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiveLogQueryService {

	private final DiveLogRepository diveLogRepository;

	/**
	 * diveLog를 받아온다 -> 내부의 DiveLogProfile Array를 가져온다. 이를 Profiles를 가지고 DiveLogProfile 만들고, 교체함
	 *
	 * @param profileId
	 * @return
	 */
	public DiveLog getUserDiveLog(Long memberId, Long profileId) {

		return diveLogRepository.findOneByMemberId(memberId, profileId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);
	}

	public DiveLog getDiveLog(Long diveLogId) {
		return diveLogRepository.findById(diveLogId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);
	}

	public Page<DiveLog> getPaginationUserDiveLogs(Long memberId, Pageable pageable) {
		return diveLogRepository.findPaginationByMemberId(memberId, pageable);
	}

	public DiveLog getById(Long diveLogId) {
		return diveLogRepository.findById(diveLogId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);
	}
}
