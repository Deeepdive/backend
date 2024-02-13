package deepdive.backend.profile.service;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilePolicyService {

    public boolean isValidCertProfile(CertOrganization organization, CertType type) {
        if (isCommonOrganization(organization) && !isCommonCertType(type)) {
            throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
        }
        return true;
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
