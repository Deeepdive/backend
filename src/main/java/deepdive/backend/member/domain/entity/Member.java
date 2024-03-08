package deepdive.backend.member.domain.entity;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.member.domain.Provider;
import deepdive.backend.profile.domain.entity.Profile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Provider provider;

	private Boolean isMarketingAgree;


	public static Member of(String email, Provider provider, String oauthId,
		Boolean isMarketingAgree) {
		Member member = new Member();
		member.email = email;
		member.provider = provider;
		member.oauthId = oauthId;
		member.isMarketingAgree = isMarketingAgree;

		return member;
	}

	public static Member oauthInfo(String email, Provider provider, String oauthId) {
		Member member = new Member();
		member.email = email;
		member.provider = provider;
		member.oauthId = oauthId;
		return member;
	}

	public void addDiveLog(DiveLog diveLog) {
		this.diveLogs.add(diveLog);
	}
}
