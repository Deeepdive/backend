package deepdive.backend.diveshop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;

@Entity
@Getter
public class DiveShop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIVE_SHOP_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Embedded
	private Address address;

	@Embedded
	private ContactInformation contactInformation;

	@Column(name = "COMMENT", length = 500)
	private String comment;

	@Column(name = "RESERVE_COUNT")
	private Integer reserveCount;

	@Column(name = "AVAILABLE_TIME")
	private String availableTime;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "DELETED_AT")
	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "diveShop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<DiveShopSport> diveShopInformation;

	@OneToMany(mappedBy = "diveShop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<DiveShopPicture> diveShopPictures;

	public static DiveShop of(String name, Address address, ContactInformation contactInformation,
		String comment, String availableTime) {
		DiveShop diveShop = new DiveShop();
		diveShop.name = name;
		diveShop.address = address;
		diveShop.contactInformation = contactInformation;
		diveShop.comment = comment;
		diveShop.availableTime = availableTime;
		diveShop.reserveCount = 0;
		diveShop.createdAt = LocalDateTime.now();

		return diveShop;
	}

	public void addReserveCount() {
		this.reserveCount++;
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

}
