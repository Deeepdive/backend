package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShopSport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiveShopSportRepository extends JpaRepository<DiveShopSport, Long> {

	@Query("SELECT dss "
		+ "FROM DiveShopSport dss "
		+ "JOIN FETCH dss.sport "
		+ "WHERE dss.diveShop.id = :diveShopId")
	List<DiveShopSport> findAllByDiveShopId(@Param("diveShopId") Long diveShopId);


	@Query("SELECT dss "
		+ "FROM DiveShopSport dss "
		+ "JOIN FETCH dss.sport "
		+ "WHERE dss.diveShop.id IN :ids")
	List<DiveShopSport> findAllByDiveShopIdIn(@Param("ids") List<Long> diveShopIds);
}
