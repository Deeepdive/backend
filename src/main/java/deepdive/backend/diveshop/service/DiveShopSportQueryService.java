package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.diveshop.repository.DiveShopSportRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveShopSportQueryService {

	private final DiveShopSportRepository diveShopSportRepository;

	public List<DiveShopSport> getByDiveShopWithSport(DiveShop diveShop) {
		return diveShopSportRepository.findByDiveShop(diveShop);
	}

	public List<DiveShopSport> getAllByDiveShopsRelationShip(List<DiveShop> allDiveShops) {
		return diveShopSportRepository.findAllByDiveShopIn(allDiveShops);
	}
}
