package deepdive.backend.member.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterMemberDto {

    private String email;
    private String provider;
    private Boolean isAlarm;
    private Boolean isMarketing;
    private String locale;
}
