package deepdive.backend.profile.domain;

import java.util.List;

public enum CertType {
    NONE, OPEN_WATER, ADVANCED_OPEN_WATER, RESCUE, DIVE_MASTER, ETC;

    public static List<CertType> common() {
        return List.of(OPEN_WATER, ADVANCED_OPEN_WATER, RESCUE, DIVE_MASTER);
    }
}
