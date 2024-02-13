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
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    private String nickName;

    // picture 부분 멀티파트로 수정하기.
    private String picture;
    private Boolean isTeacher;
    private String etc;

    @Nullable
    @Enumerated(EnumType.STRING)
    private CertOrganization organization;
    @Nullable
    @Enumerated(EnumType.STRING)
    private CertType certType;

    protected Profile(String nickName, String picture) {
        this.nickName = nickName;
        this.picture = picture;
    }

    public static Profile defaultProfile(String nickName, String picture) {

        return new Profile(nickName, picture);
    }

    public void updateDefaultProfile(String nickName, String picture) {
        this.nickName = nickName;
        this.picture = picture;
    }

    public void updateCertProfile(CertOrganization certOrganization, CertType certType,
        Boolean isTeacher) {
        this.organization = certOrganization;
        this.certType = certType;
        this.isTeacher = isTeacher;
        this.etc = null;
    }

    public void updateEtcCertProfile(CertOrganization etcOrganization, boolean isTeacher,
        String etc) {
        this.certType = null;
        this.organization = etcOrganization;
        this.isTeacher = isTeacher;
        this.etc = etc;
    }
}
