package deepdive.backend.dto.divelog;

import java.util.List;

public record DiveLogResponsePaginationDto(
	List<DiveLogResponseDto> result,
	Long totalCount) {

}
