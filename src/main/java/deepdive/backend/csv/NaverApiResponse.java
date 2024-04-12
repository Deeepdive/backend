package deepdive.backend.csv;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import deepdive.backend.diveshop.domain.Location;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverApiResponse {

	private List<Location> addresses;
}
