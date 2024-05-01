package deepdive.backend.csv;

import deepdive.backend.diveshop.domain.Address;
import deepdive.backend.diveshop.domain.ContactInformation;
import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.diveshop.domain.DiveShopPicture;
import deepdive.backend.diveshop.domain.DiveShopSport;
import deepdive.backend.diveshop.domain.Location;
import deepdive.backend.diveshop.domain.Sport;
import deepdive.backend.diveshop.repository.DiveShopPictureRepository;
import deepdive.backend.diveshop.repository.DiveShopRepository;
import deepdive.backend.diveshop.repository.DiveShopSportRepository;
import deepdive.backend.diveshop.repository.SportRepository;
import deepdive.backend.dto.diveshop.DiveShopCsvData;
import java.util.ArrayList;
import java.util.Arrays;
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

	private final NaverPlaceSearchService naverPlaceSearchService;

	private final DiveShopRepository diveShopRepository;
	private final SportRepository sportRepository;
	private final DiveShopSportRepository diveShopSportRepository;
	private final DiveShopPictureRepository diveShopPictureRepository;

	@Override
	@Transactional
	public void write(Chunk<? extends DiveShopCsvData> chunk) {
		Chunk<DiveShop> diveShops = new Chunk<>();
		List<DiveShopSport> diveShopInformations = new ArrayList<>();
		List<DiveShopPicture> diveShopPictures = new ArrayList<>();

		chunk.forEach(diveShopCsvData -> {
			DiveShop diveShop = createDiveShopByCsvData(diveShopCsvData);
			diveShops.add(diveShop);

			List<DiveShopSport> diveShopSports = createDiveShopSportByCsvData(diveShop,
				diveShopCsvData.getSportType());
			diveShopInformations.addAll(diveShopSports);

			DiveShopPicture diveShopPicture = DiveShopPicture.of(diveShop,
				diveShopCsvData.getThumbnail());
			diveShopPictures.add(diveShopPicture);

			List<DiveShopPicture> pictures = createDiveShopPictures(diveShop,
				diveShopCsvData.getImages());
			diveShopPictures.addAll(pictures);
		});
		diveShopRepository.saveAll(diveShops);
		diveShopSportRepository.saveAll(diveShopInformations);
		diveShopPictureRepository.saveAll(diveShopPictures);
	}

	private List<DiveShopSport> createDiveShopSportByCsvData(DiveShop diveShop, String sportType) {
		return Arrays.stream(sportType.split(","))
			.map(name -> sportRepository.findByName(name).orElseGet(() -> {
				Sport sport = Sport.of(name);
				return sportRepository.save(sport);
			}))
			.map(sport -> DiveShopSport.of(diveShop, sport))
			.toList();
	}

	private DiveShop createDiveShopByCsvData(DiveShopCsvData diveShopCsvData) {
		String fullAddress = diveShopCsvData.getFullAddress();
		Location location = naverPlaceSearchService.getDiveShopImages(fullAddress);
		Address address = Address.of(diveShopCsvData.getProvince(), diveShopCsvData.getCity(),
			diveShopCsvData.getFullAddress(), diveShopCsvData.getDetail());
		ContactInformation contactInformation =
			ContactInformation.of(diveShopCsvData.getPhoneNumber(), diveShopCsvData.getFax());
		return DiveShop.of(diveShopCsvData.getName(), address, contactInformation,
			diveShopCsvData.getReview_comment(), diveShopCsvData.getAvailableTime(), location,
			diveShopCsvData.getReview_title());
	}

	private List<DiveShopPicture> createDiveShopPictures(DiveShop diveShop, String images) {
		return Arrays.stream(images.split(","))
			.map(picture -> DiveShopPicture.of(diveShop, picture))
			.toList();
	}
}
