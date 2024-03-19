package deepdive.backend.profile.repository;

import deepdive.backend.profile.domain.entity.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findByNickName(String nickName);

	@Query("SELECT p.nickName "
		+ "FROM Profile p "
		+ "WHERE p IN :profiles")
	List<String> findNickNames(@Param("profiles") List<Profile> profiles);

	Optional<Profile> findByMemberId(Long memberId);

}
