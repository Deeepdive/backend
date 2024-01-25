package deepdive.backend.dto.divelog;

import java.util.List;

public record DiveLogInfoPaginationDto(List<DiveLogInfoDto> result, Long totalCount) {

}
