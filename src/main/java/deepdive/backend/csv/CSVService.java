package deepdive.backend.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import deepdive.backend.diveshop.repository.DiveShopRepository;
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

	private final DiveShopRepository diveShopRepository;


	@PostConstruct
	public void diveShopInit() {
		if (dataAlreadyLoaded(diveShopRepository)) {
			loadDiveShopFromCSV("dive-shop.csv");
		}
	}

	private boolean dataAlreadyLoaded(JpaRepository repository) {
		return repository.count() <= 0;
	}

	@Transactional
	public void loadDiveShopFromCSV(String fileName) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(
				Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
			FileReader reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader);

			csvReader.readNext(); // 도, 시 생략
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {

			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

}
