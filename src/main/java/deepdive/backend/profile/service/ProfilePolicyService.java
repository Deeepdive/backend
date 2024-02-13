package deepdive.backend.profile.service;

import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilePolicyService {

    public boolean isBlankString(String str) {
        return str == null || str.isBlank();
    }

    public boolean isValidMatchCertProfile(CertOrganization organization, CertType type) {
        return isValidCommonCertMatch(organization, type) || isValidNoneCert(organization, type);
    }

    public boolean isValidCommonCertMatch(CertOrganization organization, CertType type) {
        return isCommonOrganization(organization) && isCommonCertType(type);
    }

    public boolean isValidNoneCert(CertOrganization organization, CertType certType) {
        return organization.equals(CertOrganization.NONE) && certType.equals(CertType.NONE);
    }

    public boolean isCommonOrganization(CertOrganization certOrganization) {
        List<CertOrganization> commonCertOrganization = CertOrganization.common();

        return commonCertOrganization.contains(certOrganization);
    }

    public boolean isCommonCertType(CertType certType) {
        List<CertType> commonCertType = CertType.common();

        return commonCertType.contains(certType);
    }
}
