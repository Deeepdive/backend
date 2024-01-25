package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.entity.DiveLog;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogRepository extends JpaRepository<DiveLog, Long> {

    Page<DiveLog> findOneByMemberId(Long memberId, Pageable pageable);

    @Query("SELECT dl "
        + "FROM DiveLog dl "
        + "WHERE dl.id = :diveLogId "
        + "AND dl.member.id = :memberId")
    Optional<DiveLog> findOneByMemberId(@Param("memberId") Long memberId,
        @Param("diveLogId") Long diveLogId);

}
