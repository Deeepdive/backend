package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShopSport;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiveShopSportRepository extends JpaRepository<DiveShopSport, Long> {

	@EntityGraph(attributePaths = "sport")
	List<DiveShopSport> findAllByDiveShopId(Long diveShopId);


	@EntityGraph(attributePaths = {"sport"})
	List<DiveShopSport> findAllByDiveShopIdIn(List<Long> diveShopIds);
}
