package deepdive.backend.dto.divelog;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.ReviewType;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
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
		@NotNull
		Purpose purpose,
		@NotNull
		LocalDate diveDate,
		String center,
		String point,
		WaterType waterType,
		Long depth,
		Long diveMin,
		Long waterTemp,
		UnderWaterVisibility underWaterVisibility,
		Long airTemp,
		Weather weather,
		SuitType suitType,
		Long weight,
		WeightType weightType,
		Long startPressure,
		Long endPressure,
		Long airTankUsage,
		ReviewType reviewType,
		@NotNull
		Long starRating,
		String reviewComment,
		@Nullable
		List<Long> profiles,
		@Nullable
		List<String> imageUrls,
		@NotNull
		String city,
		@NotNull
		String country
) {

	public List<Long> profiles() {
		return profiles != null ? profiles : new ArrayList<>();
	}

	public List<String> imageUrls() {
		return imageUrls != null ? imageUrls : new ArrayList<>();
	}

	//
}
