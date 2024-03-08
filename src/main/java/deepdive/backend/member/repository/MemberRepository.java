package deepdive.backend.member.repository;

import deepdive.backend.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByOauthId(String oauthId);

	@EntityGraph(attributePaths = "profile")
	@Query("SELECT m "
		+ "FROM Member m "
		+ "WHERE m.id = :id ")
	Optional<Member> findByIdWithProfile(@Param("id") Long id);

}
