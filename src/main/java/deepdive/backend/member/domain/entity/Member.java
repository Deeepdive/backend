package deepdive.backend.member.domain.entity;

import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.member.domain.Os;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private Set<DiveLog> diveLogs = new HashSet<>();

    private String email;
    private String picture;
    private String name;
    private String oauthId;
    private String locale;
    private String provider;

    @Enumerated(value = EnumType.STRING)
    private Os os;
    private Boolean pushAllowed;
    private Boolean marketingAllowed;

    // 아래 부분은 추후 profile entity로 분리 -> 1:1, 단방향
    @Enumerated(value = EnumType.STRING)
    private CertOrganization organization;
    @Enumerated(value = EnumType.STRING)
    private CertType certType;
    private Boolean isTeacher;
    private String nickName;
    private Boolean isAlarmAgree;
    private Boolean isMarketingAgree;
    private Boolean isAgree;


    /**
     * 각기 다른 Oauth들 중, 공통 key값을 가지고 Member Entity를 생성합니다.
     * <p>
     * userProfile -> 고유id, email, provider, name, locale, picture
     *
     * @param userProfile
     * @return Member Entity
     */
    public static Member defaultInformation(UserProfile userProfile) {
        Member member = new Member();
        member.picture = userProfile.getAttributeByKey("picture");
        member.email = userProfile.getAttributeByKey("email");
        member.name = userProfile.getAttributeByKey("name");
        member.locale = userProfile.getAttributeByKey("locale");
        member.provider = userProfile.getAttributeByKey("provider");
        member.oauthId = userProfile.getAttributeByKey("id");
        return member;
    }

    public void updateCertInformation(String organization, String type, Boolean isTeacher) {
        this.isTeacher = isTeacher;
        this.organization = CertOrganization.valueOf(organization);
        this.certType = CertType.valueOf(type);
    }

    public void updateProfile(String nickName, String picture) {
        this.nickName = nickName;
        this.picture = picture;
    }

    public void updateAgreement(Boolean isAlarmAgree, Boolean isMarketingAgree) {
        this.isAgree = true;
        this.isAlarmAgree = isAlarmAgree;
        this.isMarketingAgree = isMarketingAgree;
    }

}
