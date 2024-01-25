package deepdive.backend.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogInfoPaginationDto;
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

    @Mapping(target = "diveHistory", source = "diveLog.diveHistory")
    DiveLogInfoDto toDiveLogInfoDto(DiveLog diveLog);

    DiveLogInfoPaginationDto toDiveLogInfoPaginationDto(List<DiveLogInfoDto> result,
        Long totalCount);
}
