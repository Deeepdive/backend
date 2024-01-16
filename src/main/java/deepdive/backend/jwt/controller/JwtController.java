package deepdive.backend.jwt.controller;

import deepdive.backend.jwt.domain.ReIssueDto;
import deepdive.backend.jwt.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    /**
     * access 토큰 만료 시 재발행 시작.
     * <p>
     * refreshToken과 비교 후 일치한다면 재발행, 일치하지 않을 경우 401
     *
     * @param reIssueDto
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<String> reIssue(@RequestBody @Valid ReIssueDto reIssueDto) {
        return ResponseEntity.ok(jwtService.reissueAccessToken(reIssueDto));
    }
}
