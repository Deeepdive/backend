package deepdive.backend.diveshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DIVE_SHOP_PICTURE")
public class DiveShopPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DETAIL_IMAGE", length = 1024)
	private String detailImage;

	@Column(name = "THUMB_NAIL", length = 1024)
	private String thumbNail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIVE_SHOP_ID")
	private DiveShop diveShop;

	protected DiveShopPicture(DiveShop diveShop, String thumbNail, String detailImage) {
		this.diveShop = diveShop;
		this.thumbNail = thumbNail;
		this.detailImage = detailImage;
	}

	public static DiveShopPicture of(DiveShop diveShop, String thumbNail, String url) {
		return new DiveShopPicture(diveShop, thumbNail, url);
	}
}
