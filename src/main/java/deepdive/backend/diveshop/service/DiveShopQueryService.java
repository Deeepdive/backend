package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopPicture;
import deepdive.backend.diveshop.repository.DiveShopPictureRepository;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.exception.ExceptionStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveShopQueryService {

	private final DiveShopRepository diveShopRepository;
	private final DiveShopPictureRepository diveShopPictureRepository;

//	public Page<DiveShop> getPaginationDiveShops(Pageable pageable) {
//		return diveShopRepository.getPaginationDiveShops(pageable);
//	}

	public DiveShop getByName(String name) {
		return diveShopRepository.findByName(name)
			.orElseThrow(ExceptionStatus.NOT_FOUND_SHOP::asServiceException);
	}

	public DiveShop getById(Long id) {
		return diveShopRepository.findById(id)
			.orElseThrow(ExceptionStatus.NOT_FOUND_SHOP::asServiceException);
	}

	public Long getDataCount() {
		return diveShopRepository.count();
	}


	public Page<DiveShop> getByProvinceName(String keyword, Pageable pageable) {
		return diveShopRepository.getByProvinceName(keyword, pageable);
	}

	public Page<DiveShop> getPaginationDiveShops(Pageable pageable) {
		return diveShopRepository.findAll(pageable);
	}

	public List<DiveShopPicture> getDiveShopPictures(List<DiveShop> diveShops) {
		return diveShopPictureRepository.findAllByDiveShopIn(diveShops);
	}

}
