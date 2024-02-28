package deepdive.backend.appversion.controller;

import deepdive.backend.dto.appversion.AppVersionDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/appVersion")
public class AppVersionController {

	private final JdbcTemplate jdbcTemplate;

	@GetMapping("")
	public AppVersionDto appVersion() {
		String sql = "SELECT google_min_version, ios_min_version "
			+ "FROM app_version WHERE id = 1";

		Map<String, Object> result = jdbcTemplate.queryForMap(sql);
		String googleAppVersion = (String) result.getOrDefault("google_min_version", "invalid");
		String iosAppVersion = (String) result.getOrDefault("ios_min_version", "invalid");

		return new AppVersionDto(googleAppVersion, iosAppVersion);
	}
}
