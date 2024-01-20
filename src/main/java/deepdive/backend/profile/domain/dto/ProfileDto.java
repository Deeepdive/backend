package deepdive.backend.profile.domain.dto;

import deepdive.backend.profile.domain.entity.Profile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {

    private String nickName;
    private String picture;
    private String certOrganization;
    private String certType;
    private Boolean isTeacher;

    public static ProfileDto of(Profile profile) {
        return ProfileDto.builder()
            .nickName(profile.getNickName())
            .picture(profile.getPicture())
            .isTeacher(profile.getIsTeacher())
            .certOrganization(profile.getOrganization().name())
            .certType(profile.getCertType().name())
            .build();
    }
}
