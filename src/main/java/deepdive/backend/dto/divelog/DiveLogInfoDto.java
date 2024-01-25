package deepdive.backend.dto.divelog;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import deepdive.backend.divelog.domain.AirTankInformation;
import deepdive.backend.divelog.domain.DiveHistory;
import deepdive.backend.divelog.domain.DiveInformation;
import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.Review;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderwaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;

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
    UnderwaterVisibility visibility,
    Weather weather,
    SuitType suitType,
    WeightType weightType) {

}
