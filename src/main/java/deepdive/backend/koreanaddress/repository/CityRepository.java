package deepdive.backend.koreanaddress.repository;

import deepdive.backend.koreanaddress.domain.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findByProvinceName(String name);
}
