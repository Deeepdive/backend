package deepdive.backend.profile.domain;

import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;
import lombok.Getter;

public enum Pictures {
    FIRST(1,
        "https://github.com/Deeepdive/backend/assets/92019454/a71b756d-5cd7-43c9-8980-693be851099a"),
    SECOND(2,
        "https://github.com/Deeepdive/backend/assets/92019454/e4d9e3bf-acc5-4d01-9dba-2fa68d4812c2"),
    THIRD(3,
        "https://github.com/Deeepdive/backend/assets/92019454/f764648d-5559-4bdb-99ec-73d7add5f2cd"),
    FOURTH(4,
        "https://github.com/Deeepdive/backend/assets/92019454/80106dbb-0f90-496a-b5b2-6fba7b1fadc1"),
    FIFTH(5,
        "https://github.com/Deeepdive/backend/assets/92019454/47a41bfd-dac3-4ee5-a631-487fc1142e99"),
    SIXTH(6,
        "https://github.com/Deeepdive/backend/assets/92019454/692fae28-6d71-4a94-a185-ba42de9a352f");

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
