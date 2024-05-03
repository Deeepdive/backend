package deepdive.backend.dto.diveshop;

import java.util.List;

public record DiveShopListDto(
	Long id,
	String thumbNail,
	List<String> sportTypes,
	String name,
	String location,
	String availableTime
) {

}
