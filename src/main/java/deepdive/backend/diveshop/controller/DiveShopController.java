package deepdive.backend.diveshop.controller;

import deepdive.backend.diveshop.service.DiveShopService;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import lombok.RequiredArgsConstructor;
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
	public DiveShopDataDto getAllShopInformation() {

	}

	@PostMapping("/reserve/{diveShopId}")
	public void reserve(@PathVariable(value = "diveShopId") Long diveShopId) {
		diveShopService.reserveDiveShop(diveShopId);
	}
}
