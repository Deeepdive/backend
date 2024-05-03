package deepdive.backend.mapper;

import deepdive.backend.diveshop.domain.DiveShop;
import deepdive.backend.dto.diveshop.DiveShopDataDto;
import deepdive.backend.dto.diveshop.DiveShopDetailDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DiveShopMapper {

	@Mapping(target = "province", source = "diveShop.address.province")
	@Mapping(target = "city", source = "diveShop.address.city")
	@Mapping(target = "fullAddress", source = "diveShop.address.fullAddress")
	@Mapping(target = "detail", source = "diveShop.address.detail")
	@Mapping(target = "phoneNumber", source = "diveShop.contactInformation.phoneNumber")
	@Mapping(target = "fax", source = "diveShop.contactInformation.fax")
	@Mapping(target = "latitude", source = "diveShop.location.longitude")
	@Mapping(target = "longitude", source = "diveShop.location.latitude")
	DiveShopDataDto toDiveShopDataDto(DiveShop diveShop, List<String> sportTypes,
		List<String> pictures);

	@Mapping(target = "fullAddress", source = "diveShop.address.fullAddress")
	@Mapping(target = "detail", source = "diveShop.address.detail")
	@Mapping(target = "phoneNumber", source = "diveShop.contactInformation.phoneNumber")
	@Mapping(target = "fax", source = "diveShop.contactInformation.fax")
	@Mapping(target = "latitude", source = "diveShop.location.latitude")
	@Mapping(target = "longitude", source = "diveShop.location.longitude")
	DiveShopDetailDto toDiveShopDetailDto(DiveShop diveShop, List<String> sportTypes,
		List<String> detailImages);
}
