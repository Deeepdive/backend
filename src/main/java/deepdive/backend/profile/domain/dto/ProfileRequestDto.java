package deepdive.backend.profile.domain.dto;

import lombok.Data;

@Data
public class ProfileRequestDto {

    private String nickName;
    private String picture;
    private String certOrganization;
    private String certType;
    private Boolean isTeacher;
}
