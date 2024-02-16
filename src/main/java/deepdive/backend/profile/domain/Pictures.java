package deepdive.backend.profile.domain;

import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;
import lombok.Getter;

public enum Pictures {
    FIRST(1, "testurl1"),
    SECOND(2, "testurl2"),
    THIRD(3, "testurl3"),
    FOURTH(4, "testurl4"),
    FIFTH(5, "testurl5"),
    SIXTH(6, "testurl6");

    private final int number;
    @Getter
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
