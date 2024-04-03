package deepdive.backend.diveshop.domain;

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
	@JoinColumn(name = "DIVESHOP_ID")
	private DiveShop diveShop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPORT_ID")
	private Sport sport;

	protected DiveShopSport(DiveShop diveShop, Sport sport) {
		this.diveShop = diveShop;
		this.sport = sport;
	}


	public static DiveShopSport of(DiveShop diveShop, Sport sport) {
		return new DiveShopSport(diveShop, sport);
	}
}
