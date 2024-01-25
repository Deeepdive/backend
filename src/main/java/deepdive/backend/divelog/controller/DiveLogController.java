package deepdive.backend.divelog.controller;

import deepdive.backend.divelog.service.DiveLogService;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogInfoPaginationDto;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/divelog")
@RequiredArgsConstructor
public class DiveLogController {

    private final DiveLogService diveLogService;

    /**
     * 새로운 다이브 로그 저장 요창
     *
     * @param diveLogRequestDto 지정된 양식의 diveLog 데이터
     * @return
     */
    @PostMapping("")
    public Long save(
        @RequestBody @Valid DiveLogRequestDto diveLogRequestDto) {
        return diveLogService.save(diveLogRequestDto);
    }

    /**
     * 하나의 다이브 로그 수정 요청
     *
     * @param diveLogId         수정할 다이브 로그의 ID
     * @param diveLogRequestDto 수정할 다이브 로그 데이터
     * @return 200 OK, 404 NOT_FOUND_USER
     */
    @PatchMapping("/{diveLogId}")
    public ResponseEntity update(@PathVariable Long diveLogId,
        @RequestBody @Valid DiveLogRequestDto diveLogRequestDto) {
        diveLogService.updateDiveLog(diveLogId, diveLogRequestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 하나의 다이브 로그 조회
     *
     * @param diveLogId 조회할 diveLogId
     * @return 200 OK 다이브 로그의 정보들, 404 diveLogNotFound
     */
    @GetMapping("/{diveLogId}")
    public ResponseEntity<DiveLogInfoDto> show(@PathVariable Long diveLogId) {
        DiveLogInfoDto dto = diveLogService.showDiveLog(diveLogId);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{diveLogId}")
    public void deleteDiveLog(@PathVariable Long diveLogId) {
        diveLogService.delete(diveLogId);
    }

    /**
     * 유저가 지니고 있는 모든 다이브 로그를 반환합니다.
     * <p>
     * 최신순, 오래된순, 일정 개수별로 요청을 보낸다.
     *
     * @return 유저가 지닌 다이브 로그 정보들을 Response 형식으로 반환합니다.
     */
    @GetMapping("/list")
    public DiveLogInfoPaginationDto getAllDiveLogsByAsc(Pageable pageable) {

        return diveLogService.findAllByPagination(pageable);
    }

}
