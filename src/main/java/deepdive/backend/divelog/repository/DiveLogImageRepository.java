package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.entity.DiveLogImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogImageRepository extends JpaRepository<DiveLogImage, Long> {

	List<DiveLogImage> findByDiveLogId(Long diveLogId);

	void deleteByDiveLogId(Long diveLogId);

	@Query("SELECT img "
			+ "FROM DiveLogImage img "
			+ "WHERE img.diveLogId IN :ids")
	List<DiveLogImage> findByDiveLogIds(@Param("ids") List<Long> ids);
}
