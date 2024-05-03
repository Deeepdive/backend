package deepdive.backend.member.domain.entity;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.member.domain.Provider;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Table(name = "MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
//
//	@OneToOne(mappedBy = "member",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.REMOVE)
//	private Profile profile;

	@OneToMany(cascade = CascadeType.ALL,
		targetEntity = DiveLog.class,
		fetch = FetchType.LAZY,
		mappedBy = "member")
	private List<DiveLog> diveLogs;

	@Column(name = "EMAIL")
	private String email;
	@Column(name = "OAUTH_ID")
	private String oauthId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "PROVIDER")
	private Provider provider;

	@Column(name = "IS_MARKETING_AGREE")
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
