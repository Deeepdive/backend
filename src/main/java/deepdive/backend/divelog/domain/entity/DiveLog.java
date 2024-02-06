package deepdive.backend.divelog.domain.entity;

import deepdive.backend.divelog.domain.AirTankInformation;
import deepdive.backend.divelog.domain.DiveHistory;
import deepdive.backend.divelog.domain.DiveInformation;
import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.Review;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import deepdive.backend.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class DiveLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "divelog_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Review review;
    @Embedded
    private DiveHistory diveHistory;
    @Embedded
    private AirTankInformation airTankInformation;
    @Embedded
    private DiveInformation diveInformation;

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

    public DiveLog() {

    }

    public void update(DiveLogRequestDto dto) {
        // enum 타입
        this.waterType = WaterType.valueOf(dto.waterType());
        this.purpose = Purpose.valueOf(dto.purpose());
        this.underWaterVisibility = UnderWaterVisibility.valueOf(dto.underWaterVisibility());
        this.weather = Weather.valueOf(dto.weather());
        this.suitType = SuitType.valueOf(dto.suitType());
        this.weightType = WeightType.valueOf(dto.weightType());

        // 소분류별 객체 분리
        this.review = Review.of(dto.reviewType(), dto.reviewComment());
        this.diveHistory = DiveHistory.of(dto.date(), dto.site(), dto.point(), dto.buddyIds());
        this.airTankInformation = AirTankInformation.of(dto.startPressure(), dto.endPressure(),
            dto.airTankUsage());
        this.diveInformation = DiveInformation.of(dto.depth(), dto.min(), dto.waterTemp());

        this.airTemp = dto.airTemp();
        this.weight = dto.weight();
    }

}
