package deepdive.backend.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
    nullValueMappingStrategy = RETURN_DEFAULT,
    nullValueMapMappingStrategy = RETURN_DEFAULT,
    nullValueIterableMappingStrategy = RETURN_DEFAULT)
@Component
public interface DiveLogMapper {

    DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog);

    DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog,
        List<ProfileDefaultDto> buddyProfiles);

    DiveLogResponsePaginationDto toDiveLogResponsePaginationDto(List<DiveLogResponseDto> result,
        Long totalCount);
}
