package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopPicture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiveShopPictureRepository extends JpaRepository<DiveShopPicture, Long> {

	List<DiveShopPicture> findAllByDiveShopIn(List<DiveShop> diveShops);

}
