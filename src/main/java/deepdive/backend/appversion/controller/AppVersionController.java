package deepdive.backend.appversion.controller;

import deepdive.backend.appversion.domain.AppVersion;
import deepdive.backend.appversion.repository.AppVersionRepository;
import deepdive.backend.dto.appversion.AppVersionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/appVersion")
@Slf4j
public class AppVersionController {

	private final AppVersionRepository appVersionRepository;

	@GetMapping("")
	public AppVersionDto appVersion() {
		AppVersion appversion = appVersionRepository.findTopByOrderByIdDesc();

		String googleAppVersion = appversion.getGoogleAppVersion();
		String iosAppVersion = appversion.getIosAppVersion();

		return new AppVersionDto(googleAppVersion, iosAppVersion);
	}

}
