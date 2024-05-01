package deepdive.backend.diveshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	//	private String longitude;
//	private String latitude;
	private String x;
	private String y;
}
