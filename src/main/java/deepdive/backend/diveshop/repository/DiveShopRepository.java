package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.DiveShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiveShopRepository extends JpaRepository<DiveShop, Long> {

	@Query("SELECT d "
			+ "FROM DiveShop d "
			+ "WHERE d.address.province = :province")
	Page<DiveShop> findAllByProvince(@Param(value = "province") String province, Pageable pageable);

	@Query("SELECT d "
			+ "FROM DiveShop d "
			+ "WHERE d.name LIKE %:keyword% "
			+ "OR d.address.province LIKE %:keyword% "
			+ "OR d.address.city LIKE %:keyword%")
	Page<DiveShop> findByKeyWord(@Param("keyword") String keyword, Pageable pageable);
}
