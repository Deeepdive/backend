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

    @OneToMany(cascade = CascadeType.ALL,
        targetEntity = DiveLog.class,
        fetch = FetchType.LAZY,
        mappedBy = "member")
    private List<DiveLog> diveLogs;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String email;
    private String oauthId;
    private String provider;

    @Enumerated(value = EnumType.STRING)
    private Os os;

    private Boolean isAlarmAgree;
    private Boolean isMarketingAgree;

    public static Member defaultInformation(String oauthId, String email, String provider,
        Boolean isAlarmAgree,
        Boolean isMarketingAgree) {
        Member member = new Member();
        member.oauthId = oauthId;
        member.email = email;
        member.provider = provider;
        member.isAlarmAgree = isAlarmAgree;
        member.isMarketingAgree = isMarketingAgree;

        return member;
    }

    public void updateAgreement(Boolean isAlarmAgree, Boolean isMarketingAgree) {
        this.isAlarmAgree = isAlarmAgree;
        this.isMarketingAgree = isMarketingAgree;
    }

    public void addDiveLog(DiveLog diveLog) {
        this.diveLogs.add(diveLog);
        diveLog.setMember(this);
    }

    public int getDiveLogCount() {
        return diveLogs.size();
    }
}
