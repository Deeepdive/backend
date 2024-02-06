package deepdive.backend.jwt.repository;

import deepdive.backend.jwt.domain.JsonWebToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<JsonWebToken, Long> {

    Optional<JsonWebToken> findByOauthId(String oauthId);
}
