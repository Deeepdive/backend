package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.exception.ExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiveShopQueryService {

	private final DiveShopRepository diveShopRepository;

	public DiveShop getById(Long id) {
		return diveShopRepository.findById(id)
				.orElseThrow(ExceptionStatus.NOT_FOUND_SHOP::asServiceException);
	}

	public Page<DiveShop> getPaginationDiveShops(Pageable pageable) {
		return diveShopRepository.findAll(pageable);
	}

	public Page<DiveShop> getPaginationByProvince(Pageable pageable, String province) {
		return diveShopRepository.findAllByProvince(province, pageable);
	}

	public Page<DiveShop> getByNameOrLocation(String keyword, Pageable pageable) {
		return diveShopRepository.findByKeyWord(keyword, pageable);
	}
}
