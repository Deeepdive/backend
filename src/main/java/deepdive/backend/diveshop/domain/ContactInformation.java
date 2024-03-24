package deepdive.backend.diveshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ContactInformation {

	private String phoneNumber;
	private String fax;

	public static ContactInformation of(String phoneNumber, String fax) {
		ContactInformation contactInformation = new ContactInformation();
		contactInformation.phoneNumber = phoneNumber;
		contactInformation.fax = fax;

		return contactInformation;
	}
}
