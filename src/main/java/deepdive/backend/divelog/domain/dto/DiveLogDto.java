package deepdive.backend.divelog.domain.dto;

import deepdive.backend.divelog.domain.entity.DiveLog;
import java.time.LocalDate;
import lombok.Data;

@Data
public class DiveLogDto {

    private Long id;
    private String purpose;
    private LocalDate date;
    private String site;
    private String point;
    // 실존하는 buddy인지 검증?
    private String buddy;

    private String waterType;
    private Long depth;
    private Long min;
    private Long waterTemp;

    private String view;
    private Long airTemp;
    private String weather;

    private String suitType;
    private Long weight;
    private String weightType;

    private Long startPressure;
    private Long endPressure;
    private Long airTankUsage;

    private String reviewType;
    private String comment;

    public static DiveLogDto of(DiveLog diveLog) {
        DiveLogDto diveLogDto = new DiveLogDto();
        diveLogDto.id = diveLog.getId();
        diveLogDto.purpose = diveLog.getPurpose().name();
        diveLogDto.date = diveLog.getHistory().getDate();
        diveLogDto.site = diveLog.getHistory().getBuddy();
        diveLogDto.point = diveLog.getHistory().getPoint();
        diveLogDto.buddy = diveLog.getHistory().getBuddy();
        diveLogDto.waterType = diveLog.getWaterType().name();
        diveLogDto.depth = diveLog.getDiveInformation().getDepth();
        diveLogDto.min = diveLog.getDiveInformation().getTime();
        diveLogDto.waterTemp = diveLog.getDiveInformation().getWaterTemp();
        diveLogDto.view = diveLog.getVisibility().name();
        diveLogDto.airTemp = diveLog.getAirTemp();
        diveLogDto.weather = diveLog.getWeather().name();
        diveLogDto.suitType = diveLog.getSuitType().name();
        diveLogDto.weight = diveLog.getWeight();
        diveLogDto.weightType = diveLog.getWeightType().name();
        diveLogDto.startPressure = diveLog.getAirTankInformation().getStartPressure();
        diveLogDto.endPressure = diveLog.getAirTankInformation().getEndPressure();
        diveLogDto.airTankUsage = diveLog.getAirTankInformation().getAirUsage();
        diveLogDto.reviewType = diveLog.getReview().getReviewType().name();
        diveLogDto.comment = diveLog.getReview().getComment();

        return diveLogDto;
    }
}
