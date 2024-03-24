package deepdive.backend.csv;

import deepdive.backend.diveshop.domain.Address;
import deepdive.backend.diveshop.domain.ContactInformation;
import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.dto.diveshop.DiveShopCsvData;
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

	@Override
	@Transactional
	public void write(Chunk<? extends DiveShopCsvData> chunk) throws Exception {
		Chunk<DiveShop> diveShops = new Chunk<>();

		chunk.forEach(diveShopCsvData -> {
			Address address = Address.of(diveShopCsvData.getProvince(), diveShopCsvData.getCity(),
				diveShopCsvData.getFullAddress(), diveShopCsvData.getDetail());
			ContactInformation contactInformation = ContactInformation.of(
				diveShopCsvData.getPhoneNumber(), diveShopCsvData.getFax());
			DiveShop diveShop = DiveShop.of(diveShopCsvData.getName(), address, contactInformation,
				diveShopCsvData.getComment(), diveShopCsvData.getAvailableTime());
			diveShops.add(diveShop);
		});
		diveShopRepository.saveAll(diveShops);
	}
}
