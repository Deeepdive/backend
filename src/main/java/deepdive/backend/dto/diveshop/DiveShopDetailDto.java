package deepdive.backend.dto.diveshop;

import java.util.List;

public record DiveShopDetailDto(
	Long id,
	String name,
	String reviewTitle,
	List<String> sportTypes,
	String availableTime,
	String fullAddress,
	String detail,
	String reviewComment,
	List<String> detailImages,
	String longitude,
	String latitude,
	String phoneNumber,
	String fax
) {

}
