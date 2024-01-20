package deepdive.backend.profile.controller;

import deepdive.backend.profile.domain.dto.ProfileRequestDto;
import deepdive.backend.profile.service.ProfileService;
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
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 유저의 프로필을 업데이트합니다.
     *
     * @param dto 닉네임, 사진, 알람 동의, 마케팅 동의, CertType 2개, 강사 여부
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Response> updateProfile(@RequestBody ProfileRequestDto dto) {

        profileService.update(dto);

        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.PROFILE_UPDATE_SUCCESS),
            HttpStatus.OK);
    }
}
