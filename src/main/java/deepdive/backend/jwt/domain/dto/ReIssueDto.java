package deepdive.backend.jwt.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReIssueDto {

    @NotBlank
    private String refreshToken;
}
