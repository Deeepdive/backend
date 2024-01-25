package deepdive.backend.dto.divelog;

import java.time.LocalDate;

public record DiveLogRequestDto(String purpose, LocalDate date, String site, String point,
                                String buddy,
                                String waterType, Long depth, Long min, Long waterTemp, String view,
                                Long airTemp, String weather, String suitType, Long weight,
                                String weightType, Long startPressure, Long endPressure,
                                Long airTankUsage,
                                String reviewType, String reviewComment) {

}
