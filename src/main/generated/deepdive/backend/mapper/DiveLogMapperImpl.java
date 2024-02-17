package deepdive.backend.mapper;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-17T11:25:54+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class DiveLogMapperImpl implements DiveLogMapper {

    @Override
    public DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog, List<String> buddyNames) {

        Long id = null;
        String purpose = null;
        LocalDate diveDate = null;
        String center = null;
        String point = null;
        String waterType = null;
        Long depth = null;
        Long diveMin = null;
        Long waterTemp = null;
        String underWaterVisibility = null;
        Long airTemp = null;
        String weather = null;
        String suitType = null;
        Long weight = null;
        String weightType = null;
        Long startPressure = null;
        Long endPressure = null;
        Long airTankUsage = null;
        String reviewType = null;
        String reviewComment = null;
        if ( diveLog != null ) {
            id = diveLog.getId();
            if ( diveLog.getPurpose() != null ) {
                purpose = diveLog.getPurpose().name();
            }
            diveDate = diveLog.getDiveDate();
            center = diveLog.getCenter();
            point = diveLog.getPoint();
            if ( diveLog.getWaterType() != null ) {
                waterType = diveLog.getWaterType().name();
            }
            depth = diveLog.getDepth();
            diveMin = diveLog.getDiveMin();
            waterTemp = diveLog.getWaterTemp();
            if ( diveLog.getUnderWaterVisibility() != null ) {
                underWaterVisibility = diveLog.getUnderWaterVisibility().name();
            }
            airTemp = diveLog.getAirTemp();
            if ( diveLog.getWeather() != null ) {
                weather = diveLog.getWeather().name();
            }
            if ( diveLog.getSuitType() != null ) {
                suitType = diveLog.getSuitType().name();
            }
            weight = diveLog.getWeight();
            if ( diveLog.getWeightType() != null ) {
                weightType = diveLog.getWeightType().name();
            }
            startPressure = diveLog.getStartPressure();
            endPressure = diveLog.getEndPressure();
            airTankUsage = diveLog.getAirTankUsage();
            if ( diveLog.getReviewType() != null ) {
                reviewType = diveLog.getReviewType().name();
            }
            reviewComment = diveLog.getReviewComment();
        }
        List<String> buddyNames1 = null;
        List<String> list = buddyNames;
        if ( list != null ) {
            buddyNames1 = new ArrayList<String>( list );
        }

        DiveLogInfoDto diveLogInfoDto = new DiveLogInfoDto( id, purpose, diveDate, center, point, waterType, depth, diveMin, waterTemp, underWaterVisibility, airTemp, weather, suitType, weight, weightType, startPressure, endPressure, airTankUsage, reviewType, reviewComment, buddyNames1 );

        return diveLogInfoDto;
    }

    @Override
    public DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog, List<ProfileDefaultResponseDto> buddyProfiles) {

        Long id = null;
        LocalDate diveDate = null;
        String center = null;
        String point = null;
        Purpose purpose = null;
        if ( diveLog != null ) {
            id = diveLog.getId();
            diveDate = diveLog.getDiveDate();
            center = diveLog.getCenter();
            point = diveLog.getPoint();
            purpose = diveLog.getPurpose();
        }
        List<ProfileDefaultDto> buddyProfiles1 = null;
        buddyProfiles1 = profileDefaultResponseDtoListToProfileDefaultDtoList( buddyProfiles );

        DiveLogResponseDto diveLogResponseDto = new DiveLogResponseDto( id, diveDate, center, point, purpose, buddyProfiles1 );

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

    protected ProfileDefaultDto profileDefaultResponseDtoToProfileDefaultDto(ProfileDefaultResponseDto profileDefaultResponseDto) {

        String nickName = null;
        String picture = null;
        if ( profileDefaultResponseDto != null ) {
            nickName = profileDefaultResponseDto.nickName();
            picture = profileDefaultResponseDto.picture();
        }

        ProfileDefaultDto profileDefaultDto = new ProfileDefaultDto( nickName, picture );

        if ( profileDefaultResponseDto != null ) {
        }

        return profileDefaultDto;
    }

    protected List<ProfileDefaultDto> profileDefaultResponseDtoListToProfileDefaultDtoList(List<ProfileDefaultResponseDto> list) {
        if ( list == null ) {
            return new ArrayList<ProfileDefaultDto>();
        }

        List<ProfileDefaultDto> list1 = new ArrayList<ProfileDefaultDto>( list.size() );
        for ( ProfileDefaultResponseDto profileDefaultResponseDto : list ) {
            list1.add( profileDefaultResponseDtoToProfileDefaultDto( profileDefaultResponseDto ) );
        }

        return list1;
    }
}
