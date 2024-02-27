package deepdive.backend.divelog.domain.entity;

import deepdive.backend.profile.domain.entity.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiveLogProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dive_log_id")
	private DiveLog diveLog;

	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;

	protected DiveLogProfile(DiveLog diveLog, Profile profile) {
		this.diveLog = diveLog;
		this.profile = profile;
	}

	public static DiveLogProfile of(DiveLog diveLog, Profile profile) {

		return new DiveLogProfile(diveLog, profile);
	}
}
