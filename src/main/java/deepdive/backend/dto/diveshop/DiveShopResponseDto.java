package deepdive.backend.dto.diveshop;

import java.util.List;

public record DiveShopResponseDto(
	List<DiveShopListDto> result,
	Long totalCount
) {

}
