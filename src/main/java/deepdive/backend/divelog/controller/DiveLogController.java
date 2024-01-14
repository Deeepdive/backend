package deepdive.backend.divelog.controller;

import deepdive.backend.divelog.domain.DiveLog;
import deepdive.backend.divelog.service.DiveLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiveLogController {

    private final DiveLogService diveLogService;

    @Operation(summary = "유저 저장 요청", description = "유저 등록")
    @PostMapping("v1/save")
    public ResponseEntity save(@RequestBody DiveLog log) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
