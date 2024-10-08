package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopPicture;
import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.dto.diveshop.DiveShopDetailDto;
import deepdive.backend.dto.diveshop.DiveShopListDto;
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
		List<DiveShopListDto> result = generateDiveShopDtoList(diveShops);

		return new DiveShopResponseDto(result, diveShops.getTotalElements());
	}

	public DiveShopResponseDto getDiveShopPaginationByProvince(Pageable pageable, String province) {
		Page<DiveShop> diveShops =
				diveShopQueryService.getPaginationByProvince(pageable, province);
		List<DiveShopListDto> result = generateDiveShopDtoList(diveShops);

		return new DiveShopResponseDto(result, diveShops.getTotalElements());
	}

	private List<DiveShopListDto> generateDiveShopDtoList(Page<DiveShop> diveShops) {
		List<Long> diveShopIds = diveShops.stream()
				.map(DiveShop::getId)
				.toList();
		Map<Long, List<DiveShopSport>> diveShopSportMap =
				diveShopSportQueryService.getByDiveShopIdsWithSport(diveShopIds).stream()
						.collect(
								Collectors.groupingBy(
										diveShopSport -> diveShopSport.getDiveShop().getId()));
		Map<Long, String> diveShopPictureMap =
				diveShopPictureQueryService.getByDiveShopIds(diveShopIds).stream()
						.collect(
								Collectors.toMap(
										diveShopPicture -> diveShopPicture.getDiveShop().getId(),
										DiveShopPicture::getThumbNail,
										(existingValue, newValue) -> existingValue
								)
						);
		return diveShops.stream()
				.map(diveShop -> {
					Long diveShopId = diveShop.getId();
					String location =
							diveShop.getAddress().getProvince() + " " + diveShop.getAddress()
									.getCity();
					List<String> sports = diveShopSportMap.getOrDefault(diveShopId,
									Collections.emptyList())
							.stream()
							.map(diveShopSport -> diveShopSport.getSport().getName())
							.toList();
					String thumbNail = diveShopPictureMap.getOrDefault(diveShopId, "");
					return diveShopMapper.toDiveShopListDto(diveShop, sports, thumbNail, location);
				}).toList();
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
	 * 다이브 로그 내에서 지역명, 이름을 갖고 검색 결과 반환
	 *
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	public DiveShopResponseDto getDiveShopsByKeyWord(String keyword, Pageable pageable) {
		Page<DiveShop> result = diveShopQueryService.getByNameOrLocation(keyword, pageable);

		return new DiveShopResponseDto(generateDiveShopDtoList(result), result.getTotalElements());
	}
}
