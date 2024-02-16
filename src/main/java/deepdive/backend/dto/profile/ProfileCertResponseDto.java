package deepdive.backend.dto.profile;

public record ProfileCertResponseDto(String certOrganization,
                                     String certType,
                                     Boolean isTeacher,
                                     String etc) {

}
