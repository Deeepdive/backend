package deepdive.backend.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import deepdive.backend.dto.profile.ProfileCertResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
    nullValueMappingStrategy = RETURN_DEFAULT,
    nullValueMapMappingStrategy = RETURN_DEFAULT,
    nullValueIterableMappingStrategy = RETURN_DEFAULT)
@Component
public interface ProfileMapper {

    ProfileDefaultDto toProfileDefaultDto(String nickName, String picture);

    ProfileDefaultResponseDto toProfileDefaultResponseDto(String nickName, String picture);

    ProfileCertResponseDto toProfileCertResponseDto(CertOrganization certOrganization,
        CertType certType, Boolean isTeacher, String etc);
}
