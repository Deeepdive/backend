package deepdive.backend.koreanaddress;

import deepdive.backend.koreanaddress.domain.City;
import deepdive.backend.koreanaddress.domain.Province;
import deepdive.backend.koreanaddress.repository.CityRepository;
import deepdive.backend.koreanaddress.repository.ProvinceRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KoreanAddressReader implements CommandLineRunner {

	private final CityRepository cityRepository;
	private final ProvinceRepository provinceRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Path path = Paths.get("src/main/resources/korean-address.csv");
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> values = List.of(line.split(","));
				String provinceName = values.get(0);
				log.info("provinceName = {}", provinceName);

				Province province = Province.of(provinceName);
				Province save = provinceRepository.save(province);

				List<City> cities = new ArrayList<>();
				for (int i = 1; i < values.size(); i++) {
					String cityName = values.get(i);
					log.info("cityName = {}", cityName);
					City city = City.of(cityName, save);
					cities.add(city);
				}
				cityRepository.saveAll(cities);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
