package deepdive.backend.jwt.controller;

import deepdive.backend.dto.token.TokenInfo;
import deepdive.backend.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    /**
     * access 토큰 만료 시 재발행 시작.
     * <p>
     * refreshToken과 비교 후 일치한다면 재발행 200, 일치하지 않을 경우 401
     *
     * @param reIssueDto
     * @return
     */
    @PostMapping("/reissue")
    public String reIssue(@RequestBody TokenInfo reIssueDto) {
        // TODO : 재발급 로직 재고려 해야할듯..
        return jwtService.reissueAccessToken(reIssueDto);
    }
}
