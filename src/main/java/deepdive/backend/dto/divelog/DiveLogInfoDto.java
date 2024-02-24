package deepdive.backend.dto.divelog;

import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import java.time.LocalDate;
import java.util.List;

/**
 * DiveLog 단권 조회 결과에 대한 DTO
 */
public record DiveLogInfoDto(
	Long id,
	String purpose,
	LocalDate diveDate,
	String center,
	String point,
	String waterType,
	Long depth,
	Long diveMin,
	Long waterTemp,
	String underWaterVisibility,
	Long airTemp,
	String weather,
	String suitType,
	Long weight,
	String weightType,
	Long startPressure,
	Long endPressure,
	Long airTankUsage,
	String reviewType,
	String reviewComment,
	List<ProfileDefaultResponseDto> buddiesProfile
) {

}
