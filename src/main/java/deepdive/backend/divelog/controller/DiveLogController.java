package deepdive.backend.divelog.controller;

import deepdive.backend.divelog.domain.dto.DiveLogResponseDto;
import deepdive.backend.divelog.domain.dto.DiveLogResult;
import deepdive.backend.divelog.service.DiveLogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/divelog")
@RequiredArgsConstructor
public class DiveLogController {

    private final DiveLogService diveLogService;

    //    @Operation(summary = "로그 기록 저장", description = "로그 기록을 저장합니다.")
//    @PostMapping("/save")
//    public ResponseEntity<DiveLogResponseDto> save(
//        @RequestBody @Valid DiveLogRequestDto diveLogRequestDto) {
//        AuthUserInfo authUser = AuthUserInfo.of();
//        DiveLogResponseDto dto = diveLogService.saveDiveLog(diveLogRequestDto, authUser);
////        diveLogService.save(diveLogDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
//    }
//
//    @PostMapping("")
//    public ResponseEntity<DiveLogResponseDto> update(
//        @RequestBody @Valid DiveLogRequestDto diveLogRequestDto) {
//        AuthUserInfo authUser = AuthUserInfo.of();
//        DiveLogResponseDto dto = diveLogService.udpate(diveLogRequestDto, authUser);
//
//        return ResponseEntity.status(HttpStatus.OK).body(dto);
//    }

    @GetMapping("/")
    public DiveLogResult showDiveLogs() {
        List<DiveLogResponseDto> diveLogResponseDtos = diveLogService.findLogDtos();

        return new DiveLogResult<>(diveLogResponseDtos.size(), diveLogResponseDtos);
    }

}
