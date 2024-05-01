package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShop;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiveShopRepository extends JpaRepository<DiveShop, Long> {

	//	Page<DiveShop> getPaginationDiveShops(Pageable pageable);
	Optional<DiveShop> findByName(String name);

	@Query("SELECT d FROM DiveShop d "
		+ "WHERE d.address.province = :province")
	Page<DiveShop> getByProvinceName(@Param(value = "province") String province, Pageable pageable);


	@EntityGraph(attributePaths = {"diveShopInformation", "diveShopPictures"})
	@Query("SELECT ds "
		+ "FROM DiveShop ds "
		+ "WHERE ds.id = :diveShopId")
	Optional<DiveShop> findByIdWithInformations(@Param("diveShopId") Long diveShopId);
}
