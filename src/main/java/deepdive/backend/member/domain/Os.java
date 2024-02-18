package deepdive.backend.member.domain;

import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;

public enum Os {
    IOS,
    ANDROID;

    public static Os of(String os) {
        return Arrays.stream(values())
            .filter(osName -> osName.name().equals(os))
            .findFirst()
            .orElseThrow(ExceptionStatus.INVALID_OS::asDomainException);
    }
}
