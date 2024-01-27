package deepdive.backend.mapper;

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
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T13:17:22+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class DiveLogMapperImpl implements DiveLogMapper {

    @Override
    public DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog) {

        DiveHistory diveHistory = null;
        Long id = null;
        Review review = null;
        AirTankInformation airTankInformation = null;
        DiveInformation diveInformation = null;
        Long airTemp = null;
        Long weight = null;
        Purpose purpose = null;
        WaterType waterType = null;
        UnderWaterVisibility underWaterVisibility = null;
        Weather weather = null;
        SuitType suitType = null;
        WeightType weightType = null;
        if ( diveLog != null ) {
            diveHistory = diveLog.getDiveHistory();
            id = diveLog.getId();
            review = diveLog.getReview();
            airTankInformation = diveLog.getAirTankInformation();
            diveInformation = diveLog.getDiveInformation();
            airTemp = diveLog.getAirTemp();
            weight = diveLog.getWeight();
            purpose = diveLog.getPurpose();
            waterType = diveLog.getWaterType();
            underWaterVisibility = diveLog.getUnderWaterVisibility();
            weather = diveLog.getWeather();
            suitType = diveLog.getSuitType();
            weightType = diveLog.getWeightType();
        }

        DiveLogInfoDto diveLogInfoDto = new DiveLogInfoDto( id, diveHistory, review, airTankInformation, diveInformation, airTemp, weight, purpose, waterType, underWaterVisibility, weather, suitType, weightType );

        if ( diveLog != null ) {
        }

        return diveLogInfoDto;
    }

    @Override
    public DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog) {

        DiveHistory diveHistory = null;
        Long id = null;
        Purpose purpose = null;
        if ( diveLog != null ) {
            diveHistory = diveLog.getDiveHistory();
            id = diveLog.getId();
            purpose = diveLog.getPurpose();
        }

        DiveLogResponseDto diveLogResponseDto = new DiveLogResponseDto( id, diveHistory, purpose );

        if ( diveLog != null ) {
        }

        return diveLogResponseDto;
    }

    @Override
    public DiveLogResponsePaginationDto toDiveLogResponsePaginationDto(List<DiveLogResponseDto> result, Long totalCount) {

        List<DiveLogResponseDto> result1 = null;
        List<DiveLogResponseDto> list = result;
        if ( list != null ) {
            result1 = new ArrayList<DiveLogResponseDto>( list );
        }
        Long totalCount1 = null;
        totalCount1 = totalCount;

        DiveLogResponsePaginationDto diveLogResponsePaginationDto = new DiveLogResponsePaginationDto( result1, totalCount1 );

        return diveLogResponsePaginationDto;
    }
}
