package deepdive.backend.profile.repository;

import deepdive.backend.profile.domain.entity.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByNickName(String nickName);

    List<Profile> findAllByNickNameIn(List<String> buddyNickNames);

    Optional<String> findNickNameById(Long id);
}
