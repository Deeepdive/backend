package deepdive.backend.mapper;

import deepdive.backend.dto.profile.ProfileCertResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-26T19:52:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileDefaultDto toProfileDefaultDto(String nickName, String picture) {

        String nickName1 = null;
        nickName1 = nickName;
        String picture1 = null;
        picture1 = picture;

        ProfileDefaultDto profileDefaultDto = new ProfileDefaultDto( nickName1, picture1 );

        return profileDefaultDto;
    }

    @Override
    public ProfileCertResponseDto toProfileCertResponseDto(CertOrganization certOrganization, CertType certType, Boolean isTeacher) {

        String certOrganization1 = null;
        if ( certOrganization != null ) {
            certOrganization1 = certOrganization.name();
        }
        String certType1 = null;
        if ( certType != null ) {
            certType1 = certType.name();
        }
        Boolean isTeacher1 = null;
        isTeacher1 = isTeacher;

        ProfileCertResponseDto profileCertResponseDto = new ProfileCertResponseDto( certOrganization1, certType1, isTeacher1 );

        return profileCertResponseDto;
    }
}
