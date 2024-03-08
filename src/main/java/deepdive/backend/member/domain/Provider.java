package deepdive.backend.member.domain;

import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;

public enum Provider {
	GOOGLE, KAKAO, NAVER, APPLE;

	public static Provider of(String provider) {
		return Arrays.stream(values())
			.filter(providerName -> providerName.name().equals(provider.toUpperCase()))
			.findFirst()
			.orElseThrow(ExceptionStatus.INVALID_PROVIDER::asDomainException);
	}
}
