package deepdive.backend.profile.controller;

import deepdive.backend.profile.domain.dto.ProfileDto;
import deepdive.backend.profile.service.ProfileService;
import deepdive.backend.utils.response.Response;
import deepdive.backend.utils.response.ResponseMsg;
import deepdive.backend.utils.response.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 유저의 프로필을 업데이트합니다.
     *
     * @param dto 닉네임, 사진, 알람 동의, 마케팅 동의, CertType 2개, 강사 여부
     * @return
     */
    @Operation(summary = "유저 프로필 등록")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.")
    })
    @PostMapping("/save")
    public ResponseEntity<Response> saveProfile(@RequestBody ProfileDto dto) {
        profileService.save(dto);

        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.PROFILE_UPDATE_SUCCESS),
            HttpStatus.OK);
    }

    @Operation(summary = "유저의 기본 프로필 수정 요청")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
        @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
        @ApiResponse(responseCode = "409", description = "해당 닉네임은 이미 사용중입니다.")
    })
    @PatchMapping("")
    public ResponseEntity<Response> updateDefaultProfile(@RequestBody ProfileDto dto) {
        profileService.updateDefaultProfile(dto);

        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.PROFILE_UPDATE_SUCCESS),
            HttpStatus.OK);
    }

    @Operation(summary = "자격증 관련 프로필 수정 요청")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
    })
    @PatchMapping("/cert")
    public ResponseEntity<Response> updateCertProfile(@RequestBody ProfileDto dto) {
        profileService.updateDefaultCertProfile(dto);

        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.PROFILE_UPDATE_SUCCESS),
            HttpStatus.OK);
    }

    /**
     * 유저의 사진, 닉네임이 담긴 기본 프로필 관련 정보를 반환합니다.
     *
     * @return 200 OK
     */
    @GetMapping("")
    public ResponseEntity<ProfileDto> memberProfile() {
        ProfileDto responseDTO = profileService.showMemberProfile();

        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * 유저의 자격증 발급 및 강사 관련 정보를 반환합니다.
     *
     * @return 200 OK
     */
    @GetMapping("/cert")
    public ResponseEntity<ProfileDto> certProfile() {
        ProfileDto responseDTO = profileService.showCertProfile();

        return ResponseEntity.ok().body(responseDTO);
    }
}
