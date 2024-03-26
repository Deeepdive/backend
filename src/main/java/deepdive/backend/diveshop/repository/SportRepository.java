package deepdive.backend.diveshop.repository;

import deepdive.backend.diveshop.domain.Sport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {

	Optional<Sport> findByName(String name);
}
