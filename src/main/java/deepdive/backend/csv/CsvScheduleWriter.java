package deepdive.backend.csv;

import deepdive.backend.diveshop.domain.Address;
import deepdive.backend.diveshop.domain.ContactInformation;
import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.Sport;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.diveshop.repository.SportRepository;
import deepdive.backend.dto.diveshop.DiveShopCsvData;
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

	@Override
	@Transactional
	public void write(Chunk<? extends DiveShopCsvData> chunk) throws Exception {
		Chunk<DiveShop> diveShops = new Chunk<>();

		chunk.forEach(diveShopCsvData -> {
			String sportTypes = diveShopCsvData.getSportType();
			List<String> types = List.of(sportTypes.split(","));
			List<Sport> sports =
				types.stream()
					.map(type
						-> sportRepository.findByName(type)
						.orElseGet(() -> sportRepository.save(Sport.of(type)))
					).toList();

			Address address = Address.of(diveShopCsvData.getProvince(), diveShopCsvData.getCity(),
				diveShopCsvData.getFullAddress(), diveShopCsvData.getDetail());
			ContactInformation contactInformation = ContactInformation.of(
				diveShopCsvData.getPhoneNumber(), diveShopCsvData.getFax());
			DiveShop diveShop = DiveShop.of(diveShopCsvData.getName(), address, contactInformation,
				diveShopCsvData.getComment(), diveShopCsvData.getAvailableTime(), sports);
			diveShops.add(diveShop);
		});
		diveShopRepository.saveAll(diveShops);
	}
}
