package deepdive.backend.profile.domain;

import java.util.List;

public enum CertOrganization {
    NONE, PADI, SDI, SSI, BSAC, KUDA, TDI, NAUI, CMAS, ETC;

    public static List<CertOrganization> common() {
        return List.of(PADI, SDI, SSI, BSAC, KUDA, TDI, NAUI, CMAS);
    }
}
