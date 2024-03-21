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
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class DiveLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "divelog_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Setter
	@OneToMany(mappedBy = "diveLog", cascade = CascadeType.ALL)
	private List<DiveLogProfile> profiles;

	// TODO : 나중에 객체화 하기..
	@Enumerated(value = EnumType.STRING)
	private ReviewType reviewType;
	private String reviewComment;

	private LocalDate diveDate;
	private String center;
	private String point;


	private Long startPressure;
	private Long endPressure;
	private Long airTankUsage;

	private Long depth;
	private Long diveMin;
	private Long waterTemp;


	private Long airTemp;
	private Long weight;


	@Enumerated(value = EnumType.STRING)
	private Purpose purpose;
	@Enumerated(value = EnumType.STRING)
	private WaterType waterType;
	@Enumerated(value = EnumType.STRING)
	private UnderWaterVisibility underWaterVisibility;
	@Enumerated(value = EnumType.STRING)
	private Weather weather;
	@Enumerated(value = EnumType.STRING)
	private SuitType suitType;
	@Enumerated(value = EnumType.STRING)
	private WeightType weightType;

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

		return diveLog;
	}

	public void update(DiveLogRequestDto dto) {
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
	}

}
