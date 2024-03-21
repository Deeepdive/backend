package deepdive.backend.dto.divelog;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.ReviewType;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * DiveLog 저장시 필요한 정보들
 *
 * @param purpose
 * @param diveDate
 * @param center
 * @param point
 * @param waterType
 * @param depth
 * @param diveMin
 * @param waterTemp
 * @param underWaterVisibility
 * @param airTemp
 * @param weather
 * @param suitType
 * @param weight
 * @param weightType
 * @param startPressure
 * @param endPressure
 * @param airTankUsage
 * @param reviewType
 * @param reviewComment
 * @param profiles
 */
public record DiveLogRequestDto(
	Purpose purpose,
	@NotNull
	LocalDate diveDate,
	@NotEmpty
	String center,
	@NotNull
	String point,
	WaterType waterType,
	@NotNull
	Long depth,
	@NotNull
	Long diveMin,
	@NotNull
	Long waterTemp,
	UnderWaterVisibility underWaterVisibility,
	@NotNull
	Long airTemp,
	Weather weather,
	SuitType suitType,
	@NotNull
	Long weight,
	WeightType weightType,
	@NotNull
	Long startPressure,
	@NotNull
	Long endPressure,
	@NotNull
	Long airTankUsage,
	ReviewType reviewType,
	String reviewComment,
	@Nullable
	List<Long> profiles
) {

}
