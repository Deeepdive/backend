package deepdive.backend.member.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "회원가입 진행 시 유저의 기본 정보")
public class RegisterMemberDto {

    @NotNull
    private String email;
    @NotNull
    private String provider;
    @NotNull
    private Boolean isAlarm;
    @NotNull
    private Boolean isMarketing;
    //    @NotNull
    private String locale;
}
