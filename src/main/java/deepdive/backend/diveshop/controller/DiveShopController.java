package deepdive.backend.diveshop.controller;

import deepdive.backend.diveshop.service.DiveShopService;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import deepdive.backend.dto.diveshop.DiveShopResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/diveShop")
public class DiveShopController {

	private final DiveShopService diveShopService;

	@GetMapping("/list")
	public DiveShopResponseDto getAllShopInformation(Pageable pageable) {
		return diveShopService.getDiveShopPagination(pageable);
	}

	@PostMapping("/reserve/{diveShopId}")
	public void reserve(@PathVariable(value = "diveShopId") Long diveShopId) {
		diveShopService.reserveDiveShop(diveShopId);
	}

	@GetMapping("/{diveShopId}")
	public DiveShopDataDto getDiveShopInformation(
		@PathVariable(value = "diveShopId") Long diveShopId) {
		return diveShopService.getDiveShopInformation(diveShopId);
	}

//	@GetMapping("search/{keyword}")
//	public DiveShopResponseDto getSearchShopResult(
//		@PathVariable(value = "keyword") String keyword, Pageable pageable) {
//		return diveShopService.getInformationByKeyword(keyword, pageable);
//	}
}
