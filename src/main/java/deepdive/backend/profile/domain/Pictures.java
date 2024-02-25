package deepdive.backend.profile.domain;

import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Pictures {
	FIRST(1,
		"https://velog.velcdn.com/images/saewoo1/post/9173615f-8d0d-4b96-9d01-2dbbb2a96c52/image.png"),
	SECOND(2,
		"https://velog.velcdn.com/images/saewoo1/post/20e767b4-04ec-4083-a0d5-2a56435159ff/image.png"),
	THIRD(3,
		"https://velog.velcdn.com/images/saewoo1/post/91f6abd9-2782-4066-a6a3-e2f6ea72b8a6/image.png"),
	FOURTH(4,
		"https://velog.velcdn.com/images/saewoo1/post/4c5890d1-472a-49f6-84a9-71af3d4ad0ff/image.png"),
	FIFTH(5,
		"https://velog.velcdn.com/images/saewoo1/post/ec4830be-906a-4b6b-9423-b6a44c7574c0/image.png"),
	SIXTH(6,
		"https://velog.velcdn.com/images/saewoo1/post/3fb43c2e-e234-418a-8439-ec46fadce550/image.png");

	private final int number;
	private final String url;

	Pictures(int number, String url) {
		this.number = number;
		this.url = url;
	}

	public static String getByNumber(int number) {
		return Arrays.stream(values())
			.filter(element -> element.number == number)
			.findFirst()
			.map(Pictures::getUrl)
			.orElseThrow(ExceptionStatus.INVALID_NUMBER_TYPE::asDomainException);
	}
}
