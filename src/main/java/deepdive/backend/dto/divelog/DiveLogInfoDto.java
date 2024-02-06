package deepdive.backend.dto.divelog;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
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

/**
 * DiveLog 단권 조회 결과에 대한 DTO
 *
 * @param id
 * @param diveHistory
 * @param review
 * @param airTankInformation
 * @param diveInformation
 * @param airTemp
 * @param weight
 * @param purpose
 * @param waterType
 * @param underWaterVisibility
 * @param weather
 * @param suitType
 * @param weightType
 */
public record DiveLogInfoDto(
    Long id,
    @JsonUnwrapped DiveHistory diveHistory,
    @JsonUnwrapped Review review,
    @JsonUnwrapped AirTankInformation airTankInformation,
    @JsonUnwrapped DiveInformation diveInformation,
    Long airTemp,
    Long weight,
    Purpose purpose,
    WaterType waterType,
    UnderWaterVisibility underWaterVisibility,
    Weather weather,
    SuitType suitType,
    WeightType weightType) {

}
