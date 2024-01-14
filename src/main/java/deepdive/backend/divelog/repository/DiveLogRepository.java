package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.DiveLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogRepository extends JpaRepository<DiveLog, Long> {

}
