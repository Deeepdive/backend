package deepdive.backend.divelog.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.domain.entity.DiveLogProfile;
import deepdive.backend.divelog.repository.DiveLogProfileRepository;
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
import deepdive.backend.profile.service.ProfileService;
import java.time.LocalDate;
import java.util.List;
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

	private final MemberQueryService memberQueryService;
	private final ProfileService profileService;
	private final ProfileQueryService profileQueryService;
	private final ProfileMapper profileMapper;

	private final DiveLogProfileRepository diveLogProfileRepository;


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
		diveLogCommandService.saveBuddiesProfile(diveLog, buddies);

		List<ProfileDefaultResponseDto> result = buddies.stream()
			.map(profile ->
				profileMapper.toProfileDefaultResponseDto(
					profile.getId(), profile.getNickName(), profile.getPicture()))
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
		Long memberId = AuthUserInfo.of().getMemberId();

		DiveLog diveLog = diveLogRepository.findOneByMemberId(memberId, diveLogId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);

		List<Profile> profiles = getProfilesByDiveLogId(diveLogId);
		List<ProfileDefaultResponseDto> result = profiles.stream()
			.map(profile ->
				profileMapper.toProfileDefaultResponseDto(
					profile.getId(), profile.getNickName(), profile.getPicture())
			)
			.toList();

		return diveLogMapper.toDiveLogInfoDto(diveLog, result);
	}

	private List<Profile> getProfilesByDiveLogId(Long diveLogId) {
		List<DiveLogProfile> diveLogProfiles = diveLogProfileRepository.findByDiveLogId(diveLogId);

		return diveLogProfiles.stream()
			.map(DiveLogProfile::getProfile)
			.toList();
	}

	/**
	 * 지정된 log의 정보를 업데이트 하는 기능
	 *
	 * @param diveLogId 변경할 log의 id값
	 * @param dto       새로 업데이트할 log의 정보들
	 */
	@Transactional
	public void updateDiveLog(Long diveLogId, DiveLogRequestDto dto) {
		Long memberId = AuthUserInfo.of().getMemberId();
		DiveLog diveLog = diveLogQueryService.getUserDiveLog(memberId, diveLogId);
		List<Profile> newProfiles = profileQueryService.getProfiles(dto.profiles());
		diveLogCommandService.updateBuddiesProfiles(diveLog, newProfiles);

		diveLog.update(dto);
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

		Page<DiveLog> divLogs = diveLogRepository.findAllByMemberId(memberId, pageable);

		List<DiveLogResponseDto> result = divLogs.stream()
			.map(diveLog -> {
				List<Profile> profiles = diveLog.getProfiles().stream()
					.map(DiveLogProfile::getProfile)
					.toList();
				List<ProfileDefaultResponseDto> buddiesProfiles =
					profileService.getBuddiesProfiles(profiles);
				return diveLogMapper.toDiveLogResponseDto(diveLog, buddiesProfiles);

			}).toList();

		return diveLogMapper.toDiveLogResponsePaginationDto(result, divLogs.getTotalElements());
	}

	public void delete(Long diveLogId) {
		diveLogRepository.deleteById(diveLogId);
	}
}
