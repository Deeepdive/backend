package deepdive.backend.diveshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "CITY")
	private String city;

	@Column(name = "FULL_ADDRESS")
	private String fullAddress;

	@Column(name = "DETAIL", length = 1024)
	private String detail;

	public static Address of(String province, String fullAddress, String zipCode, String detail) {
		Address address = new Address();
		address.province = province;
		address.city = fullAddress;
		address.fullAddress = zipCode;
		address.detail = detail;

		return address;
	}

}
