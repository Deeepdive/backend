package deepdive.backend.divelog.controller;

import deepdive.backend.divelog.service.DiveLogService;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/dive-logs")
public class TempDiveLogController {

	private final DiveLogService diveLogService;

	@PostMapping("")
	public DiveLogInfoDto save(@RequestBody @Valid DiveLogRequestDto dto) {
		log.warn("뭐로 들어옴요??");
		log.warn(dto.waterType().name());
		return diveLogService.save(dto);
	}

	@PatchMapping("/{diveLogId}")
	public void update(@PathVariable(value = "diveLogId") Long diveLogId,
			@RequestBody @Valid DiveLogRequestDto diveLogRequestDto) {
		diveLogService.updateDiveLog(diveLogId, diveLogRequestDto);
	}

	@GetMapping("/{diveLogId}")
	public DiveLogInfoDto show(@PathVariable(value = "diveLogId") Long diveLogId) {
		return diveLogService.showDiveLog(diveLogId);
	}

	@DeleteMapping("/{diveLogId}")
	public void deleteDiveLog(@PathVariable(value = "diveLogId") Long diveLogId) {
		diveLogService.delete(diveLogId);
	}

	@GetMapping("/list")
	public DiveLogResponsePaginationDto getAllDiveLogsByAsc(Pageable pageable) {

		return diveLogService.findAllByPagination(pageable);
	}
}
