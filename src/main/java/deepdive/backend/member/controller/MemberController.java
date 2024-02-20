package deepdive.backend.member.controller;

import deepdive.backend.dto.member.MemberLoginRequestDto;
import deepdive.backend.dto.member.MemberRegisterRequestDto;
import deepdive.backend.dto.token.TokenInfo;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    /**
     * 마케팅 동의를 마친 회원을 등록합니다.
     *
     * @param dto 유저에 대한 기본 정보 - 이메일, 발급자, 동의내역, 접속 장소
     * @return 성공 시 200, 실패 시 401
     */
    @Operation(summary = "회원 가입 신청")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "신규 유저 등록"),
        @ApiResponse(responseCode = "400", description = "SNS 정보와 email이 일치하지 않습니다."),
        @ApiResponse(responseCode = "400", description = "가입한 내역이 존재합니다. 다른 email로 시도해주세요.")
    })
    @PostMapping("/register")
    public TokenInfo registerUser(
        @Parameter(description = "회원가입 진행 시 필요한 기본 정보")
        @RequestBody @Valid MemberRegisterRequestDto dto) {

        Member member = memberService.registerMember(dto.email(), dto.provider(), dto.oauthId(),
            dto.isMarketing());
        return jwtService.generateToken(member.getId(), member.getOauthId());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> commonLogin(@RequestBody @Valid MemberLoginRequestDto dto) {
        if (!memberService.isRegisteredMember(dto.oauthId())) {
            String registerToken = jwtService.createRegisterToken(dto.oauthId());
            log.info("신규 유저 로그인");
            return ResponseEntity.ok().body(new TokenInfo(registerToken, ""));
        }
        log.info("기존 유저의 refreshToken 발급");
        Long memberId = memberService.getValidMemberByLoginInfo(dto.oauthId(), dto.email());
        return ResponseEntity.status(201).body(jwtService.generateToken(memberId, dto.oauthId()));
    }

    @DeleteMapping("")
    public void deleteMember() {
        memberService.delete();
    }
}
