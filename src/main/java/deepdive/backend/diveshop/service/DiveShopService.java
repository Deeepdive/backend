package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopPicture;
import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import deepdive.backend.dto.diveshop.DiveShopDetailDto;
import deepdive.backend.dto.diveshop.DiveShopResponseDto;
import deepdive.backend.mapper.DiveShopMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

	@Transactional
	public void reserveDiveShop(Long diveShopId) {
		diveShopCommandService.addReserveCount(diveShopId);
	}

	public DiveShopResponseDto getDiveShopPagination(Pageable pageable) {
		Page<DiveShop> diveShops = diveShopQueryService.getPaginationDiveShops(pageable);
		List<Long> diveShopIds = diveShops.stream()
			.map(DiveShop::getId)
			.toList();
		Map<Long, List<DiveShopSport>> diveShopSportMap =
			diveShopSportQueryService.getByDiveShopIdsWithSport(diveShopIds).stream()
				.collect(
					Collectors.groupingBy(diveShopSport -> diveShopSport.getDiveShop().getId()));
		Map<Long, List<DiveShopPicture>> diveShopPictureMap =
			diveShopPictureQueryService.getByDiveShopIds(diveShopIds).stream()
				.collect(Collectors.groupingBy(
					diveShopPicture -> diveShopPicture.getDiveShop().getId()));

		List<DiveShopDataDto> result = diveShops.getContent().stream()
			.map(diveShop -> {
				Long diveShopId = diveShop.getId();
				List<String> diveShopSportNames =
					diveShopSportMap.getOrDefault(diveShopId, Collections.emptyList()).stream()
						.map(dss -> dss.getSport().getName())
						.toList();
				List<String> diveShopPictureUrls =
					diveShopPictureMap.getOrDefault(diveShopId, Collections.emptyList()).stream()
						.map(DiveShopPicture::getDetailImage)
						.toList();

				return diveShopMapper.toDiveShopDataDto(diveShop, diveShopSportNames,
					diveShopPictureUrls);
			})
			.toList();

		return new DiveShopResponseDto(result, diveShops.getTotalElements());
	}


	public DiveShopDetailDto getDiveShopInformation(Long diveShopId) {
		DiveShop diveShop = diveShopQueryService.getById(diveShopId);

		List<DiveShopSport> diveShopSports =
			diveShopSportQueryService.getByDiveShopWithSport(diveShopId);
		List<String> types = diveShopSports.stream()
			.map(diveShopSport -> diveShopSport.getSport().getName())
			.toList();
		List<DiveShopPicture> diveShopPictures =
			diveShopPictureQueryService.getByDiveShopId(diveShopId);
		List<String> diveShopPictureUrls = diveShopPictures.stream()
			.map(DiveShopPicture::getDetailImage)
			.toList();

		return diveShopMapper.toDiveShopDetailDto(diveShop, types, diveShopPictureUrls);
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
