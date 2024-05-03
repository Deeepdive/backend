package deepdive.backend.mapper;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.ReviewType;
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
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-03T15:33:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class DiveLogMapperImpl implements DiveLogMapper {

    @Override
    public DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog, List<ProfileDefaultResponseDto> buddiesProfile) {

        Long id = null;
        Purpose purpose = null;
        LocalDate diveDate = null;
        String center = null;
        String point = null;
        WaterType waterType = null;
        Long depth = null;
        Long diveMin = null;
        Long waterTemp = null;
        UnderWaterVisibility underWaterVisibility = null;
        Long airTemp = null;
        Weather weather = null;
        SuitType suitType = null;
        Long weight = null;
        WeightType weightType = null;
        Long startPressure = null;
        Long endPressure = null;
        Long airTankUsage = null;
        ReviewType reviewType = null;
        String reviewComment = null;
        if ( diveLog != null ) {
            id = diveLog.getId();
            purpose = diveLog.getPurpose();
            diveDate = diveLog.getDiveDate();
            center = diveLog.getCenter();
            point = diveLog.getPoint();
            waterType = diveLog.getWaterType();
            depth = diveLog.getDepth();
            diveMin = diveLog.getDiveMin();
            waterTemp = diveLog.getWaterTemp();
            underWaterVisibility = diveLog.getUnderWaterVisibility();
            airTemp = diveLog.getAirTemp();
            weather = diveLog.getWeather();
            suitType = diveLog.getSuitType();
            weight = diveLog.getWeight();
            weightType = diveLog.getWeightType();
            startPressure = diveLog.getStartPressure();
            endPressure = diveLog.getEndPressure();
            airTankUsage = diveLog.getAirTankUsage();
            reviewType = diveLog.getReviewType();
            reviewComment = diveLog.getReviewComment();
        }
        List<ProfileDefaultResponseDto> buddiesProfile1 = null;
        List<ProfileDefaultResponseDto> list = buddiesProfile;
        if ( list != null ) {
            buddiesProfile1 = new ArrayList<ProfileDefaultResponseDto>( list );
        }

        DiveLogInfoDto diveLogInfoDto = new DiveLogInfoDto( id, purpose, diveDate, center, point, waterType, depth, diveMin, waterTemp, underWaterVisibility, airTemp, weather, suitType, weight, weightType, startPressure, endPressure, airTankUsage, reviewType, reviewComment, buddiesProfile1 );

        return diveLogInfoDto;
    }

    @Override
    public DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog, List<ProfileDefaultResponseDto> buddiesProfile) {

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
        List<ProfileDefaultDto> buddiesProfile1 = null;
        buddiesProfile1 = profileDefaultResponseDtoListToProfileDefaultDtoList( buddiesProfile );

        DiveLogResponseDto diveLogResponseDto = new DiveLogResponseDto( id, diveDate, center, point, purpose, buddiesProfile1 );

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
