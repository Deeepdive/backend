package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.divelog.repository.DiveLogProfileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveLogProfileQueryService {

	private final DiveLogProfileRepository diveLogProfileRepository;

	public List<DiveLogProfile> getByDiveLogId(Long diveLogId) {
		return diveLogProfileRepository.findByDiveLogId(diveLogId);
	}

	public List<DiveLogProfile> getByDiveLogIds(List<Long> diveLogIds) {
		return diveLogProfileRepository.findByDivLogIds(diveLogIds);
	}
}
