package deepdive.backend.csv;

import com.fasterxml.jackson.databind.ObjectMapper;
import deepdive.backend.diveshop.domain.Location;
import deepdive.backend.diveshop.repository.DiveShopRepository;
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

	private static final String TARGET_URI = "https://openapi.naver.com/v1/search/local.json";
	private final DiveShopRepository diveShopRepository;
	@Value("${naver.client}")
	private String client;
	@Value("${naver.secret}")
	private String secret;

	@Transactional
	public Location getDiveShopImages(String diveShopName) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-Naver-Client-Id", client);
		httpHeaders.set("X-Naver-Client-Secret", secret);

		String url = TARGET_URI + "?query=" + diveShopName.replace(" ", "");
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
			String.class);
		log.info("result = {}", response.getBody());
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			NaverApiResponse imageResponse = objectMapper.readValue(response.getBody(),
				NaverApiResponse.class);
			if (imageResponse.getItems().size() == 1) {
				return imageResponse.getItems().get(0);
			}
		} catch (Exception e) {
			log.error("Error processing JSON", e);
		}
		return new Location();
	}


}
