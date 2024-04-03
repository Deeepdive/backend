package deepdive.backend.mapper;

import deepdive.backend.diveshop.domain.Address;
import deepdive.backend.diveshop.domain.ContactInformation;
import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DiveShopMapper {

	DiveShopDataDto toDiveShopDataDto(DiveShop diveShop, Address address,
		ContactInformation contactInformation, List<String> sportTypes, List<String> pictures);
}
