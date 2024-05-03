package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShopSport;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiveShopSportRepository extends JpaRepository<DiveShopSport, Long> {

	@Query("SELECT dss "
		+ "FROM DiveShopSport dss "
		+ "JOIN FETCH dss.sport "
		+ "WHERE dss.diveShop.id = :diveShopId")
	List<DiveShopSport> findAllByDiveShopId(@Param("diveShopId") Long diveShopId);


	@EntityGraph(attributePaths = {"sport"})
	List<DiveShopSport> findAllByDiveShopIdIn(List<Long> diveShopIds);
}
