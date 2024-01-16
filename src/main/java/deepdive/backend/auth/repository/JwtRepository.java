package deepdive.backend.auth.repository;

import deepdive.backend.auth.domain.JsonWebToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<JsonWebToken, Long> {

    Optional<JsonWebToken> findByMemberId(Long memberId);
}
