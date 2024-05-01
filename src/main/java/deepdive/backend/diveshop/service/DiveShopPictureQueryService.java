package deepdive.backend.diveshop.service;

import deepdive.backend.diveshop.domain.DiveShopPicture;
import deepdive.backend.diveshop.repository.DiveShopPictureRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveShopPictureQueryService {

	private final DiveShopPictureRepository diveShopPictureRepository;

	public List<DiveShopPicture> getByDiveShopId(Long diveShopId) {
		return diveShopPictureRepository.findAllByDiveShopId(diveShopId);
	}

	public List<DiveShopPicture> getByDiveShopIds(List<Long> diveShopIds) {
		return diveShopPictureRepository.findAllByDiveShopIdIn(diveShopIds);
	}
}
