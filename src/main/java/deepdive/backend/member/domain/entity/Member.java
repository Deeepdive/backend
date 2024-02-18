package deepdive.backend.member.domain.entity;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.member.domain.Os;
import deepdive.backend.profile.domain.entity.Profile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL,
        targetEntity = DiveLog.class,
        fetch = FetchType.LAZY,
        mappedBy = "member")
    private List<DiveLog> diveLogs;

    private String email;
    private String oauthId;
    private String provider;

    @Enumerated(value = EnumType.STRING)
    private Os os;

    private Boolean isAlarmAgree;
    private Boolean isMarketingAgree;


    public static Member of(String email, String provider, String oauthId) {
        Member member = new Member();
        member.email = email;
        member.provider = provider;
        member.oauthId = oauthId;

        return member;
    }

    public void updateAgreement(Boolean isAlarmAgree, Boolean isMarketingAgree, String os) {
        this.isAlarmAgree = isAlarmAgree;
        this.isMarketingAgree = isMarketingAgree;
        this.os = Os.valueOf(os);
    }

    public void addDiveLog(DiveLog diveLog) {
        this.diveLogs.add(diveLog);
    }
}
