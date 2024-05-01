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
public class DiveShopPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PICTURE_ID", nullable = false, insertable = false)
	private Long id;

	@Column(name = "URL", nullable = false, updatable = false)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIVE_SHOP_ID", nullable = false, insertable = false)
	private DiveShop diveShop;

	@Column(name = "DIVE_SHOP_ID", nullable = false)
	private Long diveShopId;

	protected DiveShopPicture(Long diveShopId, String url) {
		this.diveShopId = diveShopId;
		this.url = url;
	}

	public static DiveShopPicture of(Long diveShopId, String url) {
		return new DiveShopPicture(diveShopId, url);
	}
}
