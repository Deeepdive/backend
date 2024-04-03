package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopSport;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiveShopSportRepository extends JpaRepository<DiveShopSport, Long> {

	@EntityGraph(attributePaths = "sport")
	List<DiveShopSport> findByDiveShop(DiveShop diveShop);


	@EntityGraph(attributePaths = "sport, diveShopPicture")
	List<DiveShopSport> findAllByDiveShopIn(List<DiveShop> diveShop);
}
