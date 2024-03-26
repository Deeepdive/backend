package deepdive.backend.diveshop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class DiveShop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dive_shop_id")
	private Long id;
	private String name;
	@Embedded
	private Address address;
	@Embedded
	private ContactInformation contactInformation;
	@Column(length = 500)
	private String comment;
	private Integer reserveCount;

	private String availableTime;
	// 스포츠 종목들

	@OneToMany(mappedBy = "diveShop", cascade = CascadeType.ALL)
	private List<Sport> sportTypes;

	public static DiveShop of(String name, Address address, ContactInformation contactInformation,
		String comment, String availableTime, List<Sport> sportTypes) {
		DiveShop diveShop = new DiveShop();
		diveShop.name = name;
		diveShop.address = address;
		diveShop.contactInformation = contactInformation;
		diveShop.comment = comment;
		diveShop.availableTime = availableTime;
		diveShop.reserveCount = 0;
		diveShop.sportTypes = sportTypes;

		return diveShop;
	}

	public void addReserveCount() {
		this.reserveCount++;
	}
}
