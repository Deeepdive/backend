package deepdive.backend.csv;

import com.fasterxml.jackson.databind.ObjectMapper;
import deepdive.backend.diveshop.domain.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NaverPlaceSearchService {

	private static final String TARGET_URI = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
	@Value("${naver.client}")
	private String client;
	@Value("${naver.secret}")
	private String secret;

	@Transactional
	public Location getDiveShopImages(String fullAddress) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-NCP-APIGW-API-KEY-ID", client);
		httpHeaders.set("X-NCP-APIGW-API-KEY", secret);

		String url = TARGET_URI + "?query=" + fullAddress;
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> response =
			restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		log.info("result = {}", response.getBody());
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			NaverApiResponse imageResponse =
				objectMapper.readValue(response.getBody(), NaverApiResponse.class);
			if (imageResponse.getAddresses().size() == 1) {
				return imageResponse.getAddresses().get(0);
			}
		} catch (Exception e) {
			log.error("Error processing JSON", e);
		}
		return new Location();
	}


}
