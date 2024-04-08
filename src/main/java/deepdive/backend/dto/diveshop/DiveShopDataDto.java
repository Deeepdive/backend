package deepdive.backend.dto.diveshop;

import java.util.List;

public record DiveShopDataDto(
	Long id,
	String name,
	String province,
	String city,
	String phoneNumber,
	String fax,
	String fullAddress,
	String detail,
	String comment,
	String availableTime,
	List<String> sportTypes,
	List<String> pictures,
	String latitude,
	String longitude

) {

}
