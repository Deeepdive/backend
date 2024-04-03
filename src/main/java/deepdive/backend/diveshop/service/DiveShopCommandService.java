package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.exception.ExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiveShopCommandService {

	private final DiveShopRepository diveLogRepository;

	@Transactional
	public void addReserveCount(Long diveShopId) {
		DiveShop diveShop = diveLogRepository.findById(diveShopId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_SHOP::asServiceException);

		diveShop.addReserveCount();
	}

}
