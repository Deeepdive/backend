package deepdive.backend.dto.divelog;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.ReviewType;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import deepdive.backend.utils.ValidEnum;
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
	@ValidEnum(enumClass = Purpose.class)
	String purpose,
	@NotNull
	LocalDate diveDate,
	@NotEmpty
	String center,
	@NotNull
	String point,
	@ValidEnum(enumClass = WaterType.class)
	String waterType,
	@NotNull
	Long depth,
	@NotNull
	Long diveMin,
	@NotNull
	Long waterTemp,
	@ValidEnum(enumClass = UnderWaterVisibility.class)
	String underWaterVisibility,
	@NotNull
	Long airTemp,
	@ValidEnum(enumClass = Weather.class)
	String weather,
	@ValidEnum(enumClass = SuitType.class)
	String suitType,
	@NotNull
	Long weight,
	@ValidEnum(enumClass = WeightType.class)
	String weightType,
	@NotNull
	Long startPressure,
	@NotNull
	Long endPressure,
	@NotNull
	Long airTankUsage,
	@ValidEnum(enumClass = ReviewType.class)
	String reviewType,
	String reviewComment,
	@Nullable
	List<Long> profiles
) {

}
