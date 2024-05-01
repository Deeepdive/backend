package deepdive.backend.diveshop.domain;

import deepdive.backend.exception.ExceptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiveShopSport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIVE_SHOP_SPORT_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIVESHOP_ID", nullable = false, insertable = false)
	private DiveShop diveShop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPORT_ID", nullable = false, insertable = false)
	private Sport sport;

	@Column(name = "DIVESHOP_ID", nullable = false)
	private Long diveShopId;
	@Column(name = "SPORT_ID", nullable = false)
	private Long sportId;

	protected DiveShopSport(Long diveShopId, Long sportId) {
		this.diveShopId = diveShopId;
		this.sportId = sportId;
	}


	public static DiveShopSport of(Long diveShopId, Long sportId) {
		DiveShopSport diveShopSport = new DiveShopSport(diveShopId, sportId);
		if (diveShopSport.isValid()) {
			throw ExceptionStatus.INVALID_ARGUMENT.asDomainException();
		}
		return diveShopSport;
	}

	private boolean isValid() {
		return this.sportId != null && this.diveShopId != null;
	}
}
