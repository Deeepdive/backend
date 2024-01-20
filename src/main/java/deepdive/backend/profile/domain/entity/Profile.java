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

    public static Profile of(String nickName, String picture, CertOrganization organization,
        CertType type, Boolean isTeacher) {
        Profile profile = new Profile();
        profile.nickName = nickName;
        
    }
}
