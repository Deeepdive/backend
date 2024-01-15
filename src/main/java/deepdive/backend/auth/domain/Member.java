package deepdive.backend.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    private String email;

    private String picture;

    public static Member of(UserProfile userProfile) {
        Member member = new Member();
        member.picture = (String) userProfile.getAttributes().get("picture");
        member.email = (String) userProfile.getAttributes().get("email");
        return member;
    }
}
