package deepdive.backend.member.domain;

import deepdive.backend.auth.domain.UserProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    private String email;
    private String picture;
    private String name;
    private String oauthId;
    private String locale;
    private String provider;

    /**
     * 각기 다른 Oauth들 중, 공통 key값을 가지고 Member Entity를 생성합니다.
     * <p>
     * userProfile -> 고유id, email, provider, name, locale, picture
     *
     * @param userProfile
     * @return Member Entity
     */
    public static Member of(UserProfile userProfile) {
        Member member = new Member();
        member.picture = userProfile.getAttributeByKey("picture");
        member.email = userProfile.getAttributeByKey("email");
        member.name = userProfile.getAttributeByKey("name");
        member.locale = userProfile.getAttributeByKey("locale");
        member.provider = userProfile.getAttributeByKey("provider");
        member.oauthId = userProfile.getAttributeByKey("id");
        return member;
    }
}
