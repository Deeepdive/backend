package deepdive.backend.dto.diveshop;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DiveShopCsvData {

	private String name;
	private String phoneNumber;
	private String fax;
	private String fullAddress;
	private String province;
	private String city;
	private String detail;
	private String availableTime;
	private String review;
	private String comment;
	private String sportType;
	private String thumbnail;
	private String images;


	public static List<String> getFieldNames() {
		Field[] declaredFields = DiveShopCsvData.class.getDeclaredFields();
		List<String> result = new ArrayList<>();
		for (Field declaredField : declaredFields) {
			result.add(declaredField.getName());
		}

		return result;
	}
}
