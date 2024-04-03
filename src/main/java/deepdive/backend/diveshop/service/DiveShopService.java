package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopPicture;
import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import deepdive.backend.mapper.DiveShopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiveShopService {

	private final DiveShopCommandService diveShopCommandService;
	private final DiveShopQueryService diveShopQueryService;
	private final DiveShopMapper diveShopMapper;

	private final DiveShopSportQueryService diveShopSportQueryService;
	private final DiveShopPictureQueryService diveShopPictureQueryService;

	public void reserveDiveShop(Long diveShopId) {
		diveShopCommandService.addReserveCount(diveShopId);
	}

	@Transactional
	public void addDiveShopPicture(DiveShop diveShop, String fullAddress) {

	}

	public void getDiveShopList(Pageable pageable) {
		Page<DiveShop> allDiveShops = diveShopQueryService.getPaginationDiveShops(pageable);
		List<DiveShopPicture> diveShopPictures =
			diveShopPictureQueryService.getDiveShopPictures(allDiveShops.getContent());

		List<DiveShopSport> allByDiveShopsRelationShip =
			diveShopSportQueryService.getAllByDiveShopsRelationShip(allDiveShops.getContent());
//		for (DiveShopInformation diveShopInformation : allByDiveShopsRelationShip) {
//			DiveShop diveShop = diveShopInformation.getDiveShop();
//			Sport sport = diveShopInformation.getSport();
//
//
//		}
//		Map<DiveShop, List<Sport>> diveShopListMap = diveShopInformations.stream()
//			.collect(Collectors.groupingBy(DiveShopSport::getDiveShop,
//				Collectors.mapping(DiveShopSport::getSport, Collectors.toList())));

	}


	public DiveShopDataDto getDiveShopInformation(Long diveShopId) {
		DiveShop diveShop = diveShopQueryService.getByDiveShopIdWithInformation(diveShopId);

		List<String> sportTypes =
			diveShop.getDiveShopInformation().stream()
				.map(sport -> sport.getSport().getName())
				.toList();
		List<String> diveShopPictureUrls = diveShop.getDiveShopPictures().stream()
			.map(DiveShopPicture::getUrl).toList();

		return diveShopMapper.toDiveShopDataDto(diveShop, sportTypes, diveShopPictureUrls);
	}

	/**
	 * 검색어로 들어온 해당 '도'에 위치한 다이빙샵 리스트 반환
	 *
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	public void getInformationByKeyword(String keyword, Pageable pageable) {
		Page<DiveShop> diveShops = diveShopQueryService.getByProvinceName(keyword, pageable);
	}
}
