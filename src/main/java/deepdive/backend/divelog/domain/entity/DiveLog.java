package deepdive.backend.divelog.domain.entity;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.ReviewType;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import deepdive.backend.member.domain.entity.Member;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Table(name = "DIVE_LOG")
public class DiveLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany(mappedBy = "diveLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DiveLogProfile> profiles;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "DIVE_LOG_ID")
	private List<DiveLogImage> image;

	// TODO : 나중에 객체화 하기..
	@Enumerated(value = EnumType.STRING)
	@Column(name = "REVIEW_TYPE")
	private ReviewType reviewType;
	@Column(name = "REVIEW_COMMENT")
	private String reviewComment;

	@Column(name = "DIVE_DATE")
	private LocalDate diveDate;
	@Column(name = "CENTER")
	private String center;
	@Column(name = "POINT")
	private String point;
	@Column(name = "CITY")
	private String city;
	@Column(name = "COUNTRY")
	private String country;


	@Column(name = "START_PRESSURE")
	private Long startPressure;
	@Column(name = "END_PRESSURE")
	private Long endPressure;
	@Column(name = "AIR_TANK_USAGE")
	private Long airTankUsage;

	@Column(name = "DEPTH")
	private Long depth;
	@Column(name = "DIVE_MIN")
	private Long diveMin;
	@Column(name = "WATER_TEMP")
	private Long waterTemp;


	@Column(name = "AIR_TEMP")
	private Long airTemp;
	@Column(name = "WEIGHT")
	private Long weight;


	@Enumerated(value = EnumType.STRING)
	@Column(name = "PURPOSE")
	private Purpose purpose;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "WATER_TYPE")
	private WaterType waterType;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "UNDER_WATER_VISIBILITY")
	private UnderWaterVisibility underWaterVisibility;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "WEATHER")
	private Weather weather;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "SUIT_TYPE")
	private SuitType suitType;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "WEIGHT_TYPE")
	private WeightType weightType;
	@Column(name = "STAR_RATING")
	private Long starRating;

	protected DiveLog() {

	}

	public static DiveLog of(DiveLogRequestDto dto, Member member) {
		DiveLog diveLog = new DiveLog();
		diveLog.purpose = dto.purpose();
		diveLog.underWaterVisibility = dto.underWaterVisibility();
		diveLog.weather = dto.weather();
		diveLog.suitType = dto.suitType();
		diveLog.weightType = dto.weightType();
		diveLog.reviewType = dto.reviewType();
		diveLog.waterType = dto.waterType();

		// 소분류별 객체 분리
		diveLog.reviewComment = dto.reviewComment();
		diveLog.diveDate = dto.diveDate();
		diveLog.center = dto.center();
		diveLog.point = dto.point();
		diveLog.startPressure = dto.startPressure();
		diveLog.endPressure = dto.endPressure();
		diveLog.airTankUsage = dto.airTankUsage();
		diveLog.depth = dto.depth();
		diveLog.diveMin = dto.diveMin();
		diveLog.waterTemp = dto.waterTemp();

		diveLog.airTemp = dto.airTemp();
		diveLog.weight = dto.weight();
		diveLog.member = member;
		diveLog.starRating = dto.starRating();
		diveLog.city = dto.city();
		diveLog.country = dto.country();

		return diveLog;
	}

	public void update(DiveLogRequestDto dto, List<DiveLogProfile> diveLogProfiles) {
		// enum 타입
		this.purpose = dto.purpose();
		this.underWaterVisibility = dto.underWaterVisibility();
		this.weather = dto.weather();
		this.suitType = dto.suitType();
		this.weightType = dto.weightType();
		this.reviewType = dto.reviewType();
		this.waterType = dto.waterType();

		this.reviewComment = dto.reviewComment();
		this.diveDate = dto.diveDate();
		this.center = dto.center();
		this.point = dto.point();
		this.startPressure = dto.startPressure();
		this.endPressure = dto.endPressure();
		this.airTankUsage = dto.airTankUsage();
		this.depth = dto.depth();
		this.diveMin = dto.diveMin();
		this.waterTemp = dto.waterTemp();

		this.airTemp = dto.airTemp();
		this.weight = dto.weight();
		this.profiles.clear();
		this.profiles.addAll(diveLogProfiles);
		
		this.starRating = dto.starRating();
		this.country = dto.country();
		this.city = dto.city();
	}

	public void updateProfiles(List<DiveLogProfile> diveLogProfiles) {
		this.profiles = diveLogProfiles;
	}
}
