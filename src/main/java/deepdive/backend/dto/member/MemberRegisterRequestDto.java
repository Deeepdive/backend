package deepdive.backend.dto.member;

public record MemberRegisterRequestDto(String email, String provider, Boolean isAlarm,
                                       Boolean isMarketing) {

}
