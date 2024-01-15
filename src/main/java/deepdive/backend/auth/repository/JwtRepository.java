package deepdive.backend.auth.repository;

import deepdive.backend.auth.domain.JwtToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<JwtToken, Long> {

    Optional<JwtToken> findByMemberId(Long memberId);
}
