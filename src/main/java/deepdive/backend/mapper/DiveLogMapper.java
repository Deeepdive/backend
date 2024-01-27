package deepdive.backend.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
    nullValueMappingStrategy = RETURN_DEFAULT,
    nullValueMapMappingStrategy = RETURN_DEFAULT,
    nullValueIterableMappingStrategy = RETURN_DEFAULT)
@Component
public interface DiveLogMapper {

    @Mapping(target = "diveHistory", source = "diveHistory")
    DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog);

    @Mapping(target = "diveHistory", source = "diveHistory")
    DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog);

    DiveLogResponsePaginationDto toDiveLogResponsePaginationDto(List<DiveLogResponseDto> result,
        Long totalCount);
}
