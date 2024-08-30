package deepdive.backend.dto.divelog;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.ReviewType;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import java.time.LocalDate;
import java.util.List;

/**
 * DiveLog 단권 조회 결과에 대한 DTO
 */
public record DiveLogInfoDto(
		Long id,
		Purpose purpose,
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
		String reviewComment,
		List<ProfileDefaultResponseDto> buddiesProfile,
		List<String> imageUrls
) {

}
