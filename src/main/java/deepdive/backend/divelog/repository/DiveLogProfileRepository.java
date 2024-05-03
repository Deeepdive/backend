package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.profile.domain.entity.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogProfileRepository extends JpaRepository<DiveLogProfile, Long> {

	@Query("SELECT dlp "
		+ "FROM DiveLogProfile dlp "
		+ "JOIN FETCH dlp.profile "
		+ "WHERE dlp.diveLog.id = :diveLogId")
	List<DiveLogProfile> findByDiveLogId(@Param("diveLogId") Long diveLogId);

	void deleteAllByProfile(Profile profile);

	@Query("SELECT dlp "
		+ "FROM DiveLogProfile dlp "
		+ "JOIN FETCH dlp.profile "
		+ "WHERE dlp.diveLog.id IN :ids")
	List<DiveLogProfile> findByDivLogIds(@Param("ids") List<Long> diveLogIds);
}
