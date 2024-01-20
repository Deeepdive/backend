package deepdive.backend.member.controller;

import deepdive.backend.member.domain.dto.RegisterMemberDto;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.utils.response.Response;
import deepdive.backend.utils.response.ResponseMsg;
import deepdive.backend.utils.response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    /**
     * 마케팅 동의를 마친 회원을 등록합니다.
     *
     * @param dto 유저에 대한 기본 정보 - 이메일, 발급자, 동의내역, 접속 장소
     * @return 성공 시 200, 실패 시 401
     */
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterMemberDto dto) {
        memberService.registerMember(dto);

        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.UNREGISTERED),
            HttpStatus.OK);
    }
}
