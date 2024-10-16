package deepdive.backend.appversion.repository;

import deepdive.backend.appversion.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {

	AppVersion findTopByOrderByIdDesc();
}
