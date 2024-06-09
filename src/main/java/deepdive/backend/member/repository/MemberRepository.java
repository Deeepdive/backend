package deepdive.backend.member.repository;

import deepdive.backend.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByOauthId(String oauthId);


}
