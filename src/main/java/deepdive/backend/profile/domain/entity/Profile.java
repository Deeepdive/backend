package deepdive.backend.profile.domain.entity;

import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    private String nickName;

    // picture 부분 멀티파트로 수정하기.
    private String picture;
    private Boolean isTeacher;

    @Nullable
    @Enumerated(EnumType.STRING)
    private CertOrganization organization;
    @Nullable
    @Enumerated(EnumType.STRING)
    private CertType certType;

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

    public void updateDefaultProfile(String nickName, String picture) {
        this.nickName = nickName;
        this.picture = picture;
    }

    public void updateCertProfile(String certOrganization, String certType, Boolean isTeacher) {
        this.organization = CertOrganization.valueOf(certOrganization);
        this.certType = CertType.valueOf(certType);
        this.isTeacher = isTeacher;
    }
}
