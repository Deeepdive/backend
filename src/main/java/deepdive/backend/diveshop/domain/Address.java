package deepdive.backend.diveshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

	private String province;
	private String city;
	private String fullAddress;
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
