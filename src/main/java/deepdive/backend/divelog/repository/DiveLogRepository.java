package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.entity.DiveLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogRepository extends JpaRepository<DiveLog, Long> {

    Page<DiveLog> findByMemberId(Long memberId, Pageable pageable);
}
