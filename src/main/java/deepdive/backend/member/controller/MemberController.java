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
     * 동의를 마친 최초 로그인 유저를 db에 등록합니다.
     */
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterMemberDto dto) {
        memberService.registerMember(dto);

        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.UNREGISTERED),
            HttpStatus.OK);
    }
}
