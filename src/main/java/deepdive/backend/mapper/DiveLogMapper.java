package deepdive.backend.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
    nullValueMappingStrategy = RETURN_DEFAULT,
    nullValueMapMappingStrategy = RETURN_DEFAULT,
    nullValueIterableMappingStrategy = RETURN_DEFAULT)
@Component
public interface DiveLogMapper {

    DiveLogRequestDto toDiveLogRequestDto(DiveLog diveLog);

    DiveLogResponseDto toDiveLogResponseDto(DiveLog diveLog);
}
