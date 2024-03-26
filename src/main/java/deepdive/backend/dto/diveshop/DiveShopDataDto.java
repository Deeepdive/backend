package deepdive.backend.dto.diveshop;

public record DiveShopDataDto(
	Long id,
	String name,
	String province,
	String city,
	String sport,
	String phoneNumber,
	String fax,
	String fullAddress,
	String detail,
	String comment,
	String availableTime

) {

}
