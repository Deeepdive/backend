package deepdive.backend.koreanaddress.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Entity
@Getter
public class Province {

	@OneToMany(mappedBy = "province")
	private final List<City> cities = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "korean_province_id")
	private Long id;
	private String name;

	public static Province of(String name) {
		Province province = new Province();
		province.name = name;
		return province;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Province province = (Province) o;
		return Objects.equals(getName(), province.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
