package deepdive.backend.koreanaddress.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import deepdive.backend.koreanaddress.domain.City;
import deepdive.backend.koreanaddress.domain.Province;
import deepdive.backend.koreanaddress.repository.CityRepository;
import deepdive.backend.koreanaddress.repository.ProvinceRepository;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CSVService {

	private final CityRepository cityRepository;
	private final ProvinceRepository provinceRepository;

	@PostConstruct
	public void AddressInit() {
		log.info("count ? = {}", provinceRepository.count());
		if (!dataAlreadyLoaded(provinceRepository)) {
			loadDataFromCSV("korean-address.csv");
		}
	}

	private boolean dataAlreadyLoaded(JpaRepository repository) {
		return repository.count() > 0;
	}

	@Transactional
	public void loadDataFromCSV(String fileName) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(
				Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
			FileReader reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader);

			csvReader.readNext(); // 도, 시 생략
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				String provinceName = nextRecord[0];
				Province province = provinceRepository.findByName(provinceName)
					.orElseGet(() -> {
						Province newProvince = Province.of(provinceName);
						return provinceRepository.save(newProvince);
					});

				for (int i = 1; i < nextRecord.length; i++) {
					String cityName = nextRecord[i];
					City city = City.of(cityName, province);
					cityRepository.save(city);
				}
			}
			csvReader.close();
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

}
