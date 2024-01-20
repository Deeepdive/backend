package deepdive.backend.profile.domain.entity;

import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    private String nickName;
    private String picture;
    private CertOrganization organization;
    private CertType certType;
    private Boolean isTeacher;

    // TODO : service 단으로 분리
    public static Profile of(String nickName, String picture, String organization,
        String type, Boolean isTeacher) {
        Profile profile = new Profile();
        profile.nickName = nickName;
        profile.picture = picture;
        profile.organization = CertOrganization.valueOf(organization);
        profile.certType = CertType.valueOf(type);
        profile.isTeacher = isTeacher;

        return profile;
    }
}
