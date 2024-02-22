package deepdive.backend.dto.profile;

public record ProfileResponseDto(
	String nickName,
	String picture,
	Boolean isTeacher,
	String certOrganization,
	String certType,
	String etc
) {

}
