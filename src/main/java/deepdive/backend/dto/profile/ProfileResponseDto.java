package deepdive.backend.dto.profile;

public record ProfileResponseDto(
    String nickName,
    String picture,
    Boolean isTeacher,
    String organization,
    String certType,
    String etc
) {

}
