package deepdive.backend.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReIssueDto {

    @NotBlank
    private Long memberId;

    @NotBlank
    private String refreshToken;
}
