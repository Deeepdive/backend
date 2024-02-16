package deepdive.backend.profile.domain;

import deepdive.backend.exception.ExceptionStatus;
import java.util.Arrays;
import java.util.List;

public enum CertOrganization {
    NONE, PADI, SDI, SSI, BSAC, KUDA, TDI, NAUI, CMAS, ETC;

    public static List<CertOrganization> common() {
        return List.of(PADI, SDI, SSI, BSAC, KUDA, TDI, NAUI, CMAS);
    }

    public static CertOrganization of(String organizationName) {
        return Arrays.stream(values())
            .filter(certOrganization -> certOrganization.name().equals(organizationName))
            .findFirst()
            .orElseThrow(ExceptionStatus.INVALID_ORGANIZATION_TYPE::asDomainException);
    }
}
