package deepdive.backend.csv;

import deepdive.backend.diveshop.domain.Address;
import deepdive.backend.diveshop.domain.ContactInformation;
import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.diveshop.domain.Sport;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.diveshop.repository.DiveShopSportRepository;
import deepdive.backend.diveshop.repository.SportRepository;
import deepdive.backend.dto.diveshop.DiveShopCsvData;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsvScheduleWriter implements ItemWriter<DiveShopCsvData> {

	private final DiveShopRepository diveShopRepository;
	private final SportRepository sportRepository;
	private final DiveShopSportRepository diveShopSportRepository;

	@Override
	@Transactional
	public void write(Chunk<? extends DiveShopCsvData> chunk) throws Exception {
		Chunk<DiveShop> diveShops = new Chunk<>();
		List<DiveShopSport> diveShopInformations = new ArrayList<>();

		chunk.forEach(diveShopCsvData -> {
			Address address = Address.of(diveShopCsvData.getProvince(), diveShopCsvData.getCity(),
				diveShopCsvData.getFullAddress(), diveShopCsvData.getDetail());
			ContactInformation contactInformation = ContactInformation.of(
				diveShopCsvData.getPhoneNumber(), diveShopCsvData.getFax());
			DiveShop diveShop = DiveShop.of(diveShopCsvData.getName(), address, contactInformation,
				diveShopCsvData.getComment(), diveShopCsvData.getAvailableTime());
			diveShops.add(diveShop);

			String sportTypes = diveShopCsvData.getSportType();
			List<String> types = List.of(sportTypes.split(","));
			for (String type : types) {
				Sport sport = sportRepository.findByName(type)
					.orElseGet(() -> {
						Sport newSport = Sport.of(type);
						return sportRepository.save(newSport);
					});
				diveShopInformations.add(DiveShopSport.of(diveShop, sport));
			}
		});
		diveShopRepository.saveAll(diveShops);
		diveShopSportRepository.saveAll(diveShopInformations);
	}
}
