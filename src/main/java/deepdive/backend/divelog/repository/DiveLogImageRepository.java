package deepdive.backend.divelog.repository;

import deepdive.backend.divelog.domain.entity.DiveLogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiveLogImageRepository extends JpaRepository<DiveLogImage, Long> {


}
