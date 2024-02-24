package deepdive.backend.divelog.domain;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.profile.domain.entity.Profile;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//@Entity
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
}
