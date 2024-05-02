package deepdive.backend.diveshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Location {

	@Column(name = "LONGITUDE")
	private String longitude;
	@Column(name = "LATITUDE")
	private String latitude;

}
