package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.AirTankInformation;
import deepdive.backend.divelog.domain.DiveHistory;
import deepdive.backend.divelog.domain.DiveInformation;
import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.Review;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderWaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.dto.divelog.DiveLogInfoDto;
import deepdive.backend.dto.divelog.DiveLogRequestDto;
import deepdive.backend.dto.divelog.DiveLogResponseDto;
import deepdive.backend.dto.divelog.DiveLogResponsePaginationDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.mapper.DiveLogMapper;
import deepdive.backend.mapper.ProfileMapper;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.service.ProfileService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiveLogService {

    private final DiveLogRepository diveLogRepository;
    private final MemberService memberService;
    private final DiveLogMapper diveLogMapper;

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public DiveLog convertToDiveLog(DiveLogRequestDto dto) {
        return DiveLog.builder()
            .diveHistory(DiveHistory.of(dto.date(), dto.site(), dto.point(), dto.buddyIds()))
            .review(Review.of(dto.reviewType(), dto.reviewComment()))
            .airTankInformation(AirTankInformation.of(dto.startPressure(), dto.endPressure(),
                dto.airTankUsage()))
            .diveInformation(DiveInformation.of(dto.depth(), dto.min(), dto.waterTemp()))
            .airTemp(dto.airTemp())
            .weight(dto.weight())
            .purpose(Purpose.valueOf(dto.purpose()))
            .waterType(WaterType.valueOf(dto.waterType()))
            .underWaterVisibility(UnderWaterVisibility.valueOf(dto.underWaterVisibility()))
            .weather(Weather.valueOf(dto.weather()))
            .suitType(SuitType.valueOf(dto.suitType()))
            .weightType(WeightType.valueOf(dto.weightType()))
            .member(memberService.getByOauthId())
            .build();
    }

    @Transactional
    public Long save(DiveLogRequestDto dto) {
        // TODO : 중복해서 같은 로직을 저장하려 하는 경우 -> 기준은?
        profileService.getBuddiesProfiles(dto.buddyIds());
        DiveLog diveLog = convertToDiveLog(dto);
        DiveLog savedDiveLog = diveLogRepository.save(diveLog);

        return savedDiveLog.getId();
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
        Member member = memberService.getByOauthId();

        DiveLog diveLog = diveLogRepository.findOneByMemberId(member.getId(), diveLogId)
            .orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);

        log.info("diveLog info = {}", diveLog);
        return diveLogMapper.toDiveLogInfoDto(diveLog);
    }

    /**
     * 지정된 log의 정보를 업데이트 하는 기능
     *
     * @param diveLogId 변경할 log의 id값
     * @param dto       새로 업데이트할 log의 정보들
     */
    @Transactional
    public void updateDiveLog(Long diveLogId, DiveLogRequestDto dto) {
        Member member = memberService.getByOauthId();

        DiveLog diveLog = diveLogRepository.findOneByMemberId(member.getId(), diveLogId)
            .orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);

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
        Member member = memberService.getByOauthId();

        Page<DiveLog> divLogs = diveLogRepository.findAllByMemberId(member.getId(), pageable);
        List<DiveLogResponseDto> result = divLogs.stream()
            .map(diveLog -> {
                List<Long> buddyIds = diveLog.getDiveHistory().getBuddyIds();
                List<ProfileDefaultDto> buddiesProfiles = profileService.getBuddiesProfiles(
                    buddyIds);
                return diveLogMapper.toDiveLogResponseDto(diveLog, buddiesProfiles);

            }).toList();
        
        return diveLogMapper.toDiveLogResponsePaginationDto(result, divLogs.getTotalElements());
    }

    public void delete(Long diveLogId) {
        diveLogRepository.deleteById(diveLogId);
    }

}
