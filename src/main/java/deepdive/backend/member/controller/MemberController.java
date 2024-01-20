package deepdive.backend.member.controller;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.member.domain.dto.RegisterMemberDto;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.domain.dto.ProfileRequestDto;
import deepdive.backend.utils.response.Response;
import deepdive.backend.utils.response.ResponseMsg;
import deepdive.backend.utils.response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 프로필 설정 및 업데이트
     *
     * @param dto 프로필 사진, 닉네임
     * @return HttpStatus.ok
     */
    @PostMapping("/profile")
    public ResponseEntity setProfile(@RequestBody ProfileRequestDto dto) {
        AuthUserInfo authUser = AuthUserInfo.of();

        memberService.updateProfile(authUser, dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 자격증 관련 설정 및 업데이트
     *
     * @param dto 자격증 발급단체, 유형, 강사인지
     * @return HttpStatus.ok
     */
    @PostMapping("/cert")
    public ResponseEntity setCert(@RequestBody ProfileRequestDto dto) {
        AuthUserInfo authUser = AuthUserInfo.of();

        memberService.updateCertInformation(authUser, dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인한 유저들의 동의사항을 업데이트 합니다.
     *
     * @param isAlarmAgree     알람 내역과 관련한 약관 동의 여부
     * @param isMarketingAgree 마케팅 관련 약관 동의 여부
     * @return 성공 시 200, 실패시 401
     */
    @PostMapping("/agreement")
    public ResponseEntity setAgreement(@RequestParam Boolean isAlarmAgree,
        @RequestParam Boolean isMarketingAgree) {

        AuthUserInfo authUser = AuthUserInfo.of();
        memberService.updateAgreement(authUser, isAlarmAgree, isMarketingAgree);
        return ResponseEntity.ok().build();
    }

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
