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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Table(name = "DIVE_SHOP")
public class DiveShop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Embedded
	private Address address;

	@Embedded
	private ContactInformation contactInformation;

	@Column(name = "REVIEW_COMMENT", length = 1024)
	private String reviewComment;
	@Column(name = "REVIEW_TITLE", length = 1024)
	private String reviewTitle;

	@Column(name = "RESERVE_COUNT")
	private Integer reserveCount;

	@Column(name = "AVAILABLE_TIME")
	private String availableTime;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "DELETED_AT")
	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "diveShop", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<DiveShopSport> diveShopInformation;

	@OneToMany(mappedBy = "diveShop", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<DiveShopPicture> diveShopPictures;

	@Embedded
	private Location location;

	public static DiveShop of(String name, Address address, ContactInformation contactInformation,
		String comment, String availableTime, Location location, String review_title) {
		DiveShop diveShop = new DiveShop();
		diveShop.name = name;
		diveShop.address = address;
		diveShop.contactInformation = contactInformation;
		diveShop.reviewTitle = review_title;
		diveShop.reviewComment = comment;
		diveShop.availableTime = availableTime;
		diveShop.reserveCount = 0;
		diveShop.createdAt = LocalDateTime.now();
		diveShop.location = location;

		return diveShop;
	}

	public void addReserveCount() {
		this.reserveCount++;
	}

	public String getFullAddress() {
		return address.getFullAddress();
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

}
