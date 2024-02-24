package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.DiveLogProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogProfileRepository extends JpaRepository<DiveLogProfile, Long> {

	List<DiveLogProfile> findByDiveLogId(Long diveLogId);

}
