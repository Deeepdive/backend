package deepdive.backend.koreanaddress.repository;

import deepdive.backend.koreanaddress.domain.Province;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

	Optional<Province> findByName(String name);
}
