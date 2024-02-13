package deepdive.backend.profile.domain;

import deepdive.backend.exception.DomainException;
import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;
import java.util.List;

public enum CertType {
    NONE, OPEN_WATER, ADVANCED_OPEN_WATER, RESCUE, DIVE_MASTER, ETC;

    public static List<CertType> common() {
        return List.of(OPEN_WATER, ADVANCED_OPEN_WATER, RESCUE, DIVE_MASTER);
    }

    public static CertType of(String typeName) {
        return Arrays.stream(values())
            .filter(certType -> certType.name().equals(typeName))
            .findFirst()
            .orElseThrow(() -> new DomainException(ExceptionStatus.INVALID_TYPE));
    }
}
