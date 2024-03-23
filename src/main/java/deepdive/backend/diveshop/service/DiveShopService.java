package deepdive.backend.diveshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveShopService {

	private final DiveShopCommandService diveShopCommandService;

	public void reserveDiveShop(Long diveShopId) {
		diveShopCommandService.addReserveCount(diveShopId);
	}
}
