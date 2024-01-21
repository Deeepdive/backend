package deepdive.backend.profile.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ProfileDto {

    private String nickName;
    private String picture;
    private String certOrganization;
    private String certType;
    private Boolean isTeacher;


    public ProfileDto() {

    }

    @Builder
    public ProfileDto(String nickName, String picture, String certOrganization, String certType,
        Boolean isTeacher) {
        this.nickName = nickName;
        this.picture = picture;
        this.certOrganization = certOrganization;
        this.certType = certType;
        this.isTeacher = isTeacher;
    }
}
