package deepdive.backend.divelog.domain.entity;

import deepdive.backend.profile.domain.entity.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DIVE_LOG_PROFILE")
public class DiveLogProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIVE_LOG_ID")
	private DiveLog diveLog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFILE_ID")
	private Profile profile;

	protected DiveLogProfile(DiveLog diveLog, Profile profile) {
		this.diveLog = diveLog;
		this.profile = profile;
	}

	public static DiveLogProfile of(DiveLog diveLog, Profile profile) {

		return new DiveLogProfile(diveLog, profile);
	}
}
