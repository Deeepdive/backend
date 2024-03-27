package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import deepdive.backend.dto.diveshop.DiveShopResponseDto;
import deepdive.backend.mapper.DiveShopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveShopService {

	private final DiveShopCommandService diveShopCommandService;
	private final DiveShopQueryService diveShopQueryService;
	private final DiveShopMapper diveShopMapper;

	public void reserveDiveShop(Long diveShopId) {
		diveShopCommandService.addReserveCount(diveShopId);
	}

//	public DiveShopDataDto getAllDiveShops(Pageable pageable) {
//		Page<DiveShop> diveShops = diveShopQueryService.getPaginationDiveShops(pageable);
//
//
//	}

//	public DiveShopDataDto diveShopInformation(String name) {
//		DiveShop diveShop = diveShopQueryService.getByName(name);
//		return diveShopMapper.toDiveShopInformationDataDto(diveShop);
//	}

	public Long getDiveShopInitDataCount() {
		return diveShopQueryService.getDataCount();
	}


	public DiveShopDataDto getDiveShopInformation(Long diveShopId) {
		DiveShop diveShop = diveShopQueryService.getById(diveShopId);

		return diveShopMapper.toDiveShopDataDto(diveShop, diveShop.getAddress(),
			diveShop.getContactInformation());
	}

	/**
	 * 검색어로 들어온 해당 '도'에 위치한 다이빙샵 리스트 반환
	 *
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	public DiveShopResponseDto getInformationByKeyword(String keyword, Pageable pageable) {
		Page<DiveShop> diveShops = diveShopQueryService.getByProvinceName(keyword, pageable);
		List<DiveShopDataDto> result = diveShops.stream()
			.map(diveShop -> diveShopMapper.toDiveShopDataDto(diveShop, diveShop.getAddress(),
				diveShop.getContactInformation()))
			.toList();
		return new DiveShopResponseDto(result, diveShops.getTotalElements());
	}
}
