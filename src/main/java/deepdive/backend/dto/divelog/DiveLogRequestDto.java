package deepdive.backend.dto.divelog;

import java.time.LocalDate;

public record DiveLogRequestDto(String purpose, LocalDate date, String site, String point,
                                String buddies,
                                String waterType, Long depth, Long min, Long waterTemp,
                                String underWaterVisibility,
                                Long airTemp, String weather, String suitType, Long weight,
                                String weightType, Long startPressure, Long endPressure,
                                Long airTankUsage,
                                String reviewType, String reviewComment) {

}
