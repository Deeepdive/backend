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
import deepdive.backend.dto.profile.ProfileDefaultDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-09T15:07:32+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class DiveLogMapperImpl implements DiveLogMapper {

    @Override
    public DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog) {

        if ( diveLog != null ) {
        }

        Long id = null;
        Review review = null;
        DiveHistory diveHistory = null;
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

        DiveLogInfoDto diveLogInfoDto = new DiveLogInfoDto( id, review, diveHistory, airTankInformation, diveInformation, airTemp, weight, purpose, waterType, underWaterVisibility, weather, suitType, weightType );

        if ( diveLog != null ) {
        }

        return diveLogInfoDto;
    }

    @Override
    public DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog, List<ProfileDefaultDto> buddyProfiles) {

        List<ProfileDefaultDto> buddyProfiles1 = null;
        List<ProfileDefaultDto> list = buddyProfiles;
        if ( list != null ) {
            buddyProfiles1 = new ArrayList<ProfileDefaultDto>( list );
        }

        Long id = null;
        DiveHistory diveHistory = null;
        Purpose purpose = null;

        DiveLogResponseDto diveLogResponseDto = new DiveLogResponseDto( id, diveHistory, purpose, buddyProfiles1 );

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
