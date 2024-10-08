package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.diveshop.repository.DiveShopSportRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveShopSportQueryService {

	private final DiveShopSportRepository diveShopSportRepository;

	public List<DiveShopSport> getByDiveShopWithSport(Long diveShopId) {
		return diveShopSportRepository.findAllByDiveShopId(diveShopId);
	}

	public List<DiveShopSport> getByDiveShopIdsWithSport(List<Long> diveShopIds) {
		return diveShopSportRepository.findAllByDiveShopIdIn(diveShopIds);
	}
}
