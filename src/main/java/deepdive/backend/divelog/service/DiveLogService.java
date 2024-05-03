package deepdive.backend.divelog.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.mapper.DiveLogMapper;
import deepdive.backend.mapper.ProfileMapper;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberQueryService;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.service.ProfileQueryService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiveLogService {

	private static final Integer AVAILABLE_DATE = 2;

	private final DiveLogRepository diveLogRepository;
	private final DiveLogMapper diveLogMapper;
	private final DiveLogQueryService diveLogQueryService;
	private final DiveLogCommandService diveLogCommandService;

	private final DiveLogProfileQueryService diveLogProfileQueryService;
	private final DiveLogProfileCommandService diveLogProfileCommandService;

	private final MemberQueryService memberQueryService;
	private final ProfileQueryService profileQueryService;
	private final ProfileMapper profileMapper;


	/**
	 * DiveLog 를 저장하는 기능
	 * <p>
	 * dto를 통해 DiveLog를 만들고, member, profiles를 세팅
	 * <p>
	 * 컬럼이 너무 많아서 dto에 의존하는 형식ㅇ..
	 *
	 * @param dto
	 * @return 저장된 diveLog id
	 */
	@Transactional
	public DiveLogInfoDto save(DiveLogRequestDto dto) {
		validateDiveDate(dto.diveDate());
		Member member = memberQueryService.getMember();
		DiveLog diveLog = diveLogRepository.save(DiveLog.of(dto, member));

		List<Profile> buddies = profileQueryService.getProfiles(dto.profiles());
		diveLogProfileCommandService.saveBuddiesProfile(diveLog, buddies);

		List<ProfileDefaultResponseDto> result = buddies.stream()
			.map(profileMapper::toProfileDefaultResponseDto)
			.toList();
		return diveLogMapper.toDiveLogInfoDto(diveLog, result);
	}

	private void validateDiveDate(LocalDate localDate) {
		LocalDate limitDate = LocalDate.now().plusDays(AVAILABLE_DATE);

		if (localDate.isAfter(limitDate)) {
			throw ExceptionStatus.INVALID_DIVE_DATE.asServiceException();
		}
	}

	/**
	 * 단권의 diveLog를 조회하는 기능
	 * <p>
	 * 유저의 ID값을 FK로 갖는 diveLog를 반환합니다.
	 *
	 * @param diveLogId 조회할 diveLogId
	 * @return diveLogInfo
	 */
	public DiveLogInfoDto showDiveLog(Long diveLogId) {
		DiveLog diveLog = diveLogQueryService.getDiveLog(diveLogId);

		List<DiveLogProfile> buddiesProfile =
			diveLogProfileQueryService.getByDiveLogId(diveLog.getId());
		List<ProfileDefaultResponseDto> result = buddiesProfile.stream()
			.map(DiveLogProfile::getProfile)
			.map(profileMapper::toProfileDefaultResponseDto)
			.toList();

		return diveLogMapper.toDiveLogInfoDto(diveLog, result);
	}

	/**
	 * 지정된 log의 정보를 업데이트 하는 기능
	 *
	 * @param diveLogId 변경할 log의 id값
	 * @param dto       새로 업데이트할 log의 정보들
	 */
	@Transactional
	public void updateDiveLog(Long diveLogId, DiveLogRequestDto dto) {
		DiveLog diveLog = diveLogQueryService.getById(diveLogId);
		List<Profile> newProfiles = profileQueryService.getProfiles(dto.profiles());
		List<DiveLogProfile> buddiesProfiles = diveLogCommandService.createBuddiesProfiles(diveLog,
			newProfiles);
		log.warn("새로운 관계 준비 완료");

		diveLog.update(dto, buddiesProfiles);
	}

	/**
	 * 유저가 작성한 다이브 로그들을 DTO 형식으로 반환
	 * <p>
	 * member가 지닌 diveLog들을 pagination 해서 반환
	 * <p>
	 * 오름차순, 내림차순 정렬
	 *
	 * @return Page 형식, 간략화한 diveLogResponse
	 */
	public DiveLogResponsePaginationDto findAllByPagination(Pageable pageable) {
		Long memberId = AuthUserInfo.of().getMemberId();

		Page<DiveLog> diveLogs = diveLogQueryService.getPaginationUserDiveLogs(memberId, pageable);
		List<Long> diveLogIds = diveLogs.stream()
			.map(DiveLog::getId)
			.toList();
		Map<Long, List<DiveLogProfile>> diveLogProfileMap =
			diveLogProfileQueryService.getByDiveLogIds(diveLogIds).stream()
				.collect(
					Collectors.groupingBy(diveLogProfile -> diveLogProfile.getDiveLog().getId()));

		List<DiveLogResponseDto> result = diveLogs.stream()
			.map(diveLog -> {
				Long diveLogId = diveLog.getId();
				List<ProfileDefaultResponseDto> profileDtos =
					diveLogProfileMap.getOrDefault(diveLogId, Collections.emptyList())
						.stream()
						.map(DiveLogProfile::getProfile)
						.map(profileMapper::toProfileDefaultResponseDto)
						.toList();
				return diveLogMapper.toDiveLogResponseDto(diveLog, profileDtos);
			}).toList();

		return diveLogMapper.toDiveLogResponsePaginationDto(result, diveLogs.getTotalElements());
	}

	@Transactional
	public void delete(Long diveLogId) {
		Long memberId = AuthUserInfo.of().getMemberId();
		diveLogCommandService.deleteByUser(memberId, diveLogId);
	}
}
