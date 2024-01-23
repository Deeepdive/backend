package deepdive.backend.divelog.service;

import deepdive.backend.commonexception.ExceptionStatus;
import deepdive.backend.divelog.domain.AirTankInformation;
import deepdive.backend.divelog.domain.DiveHistory;
import deepdive.backend.divelog.domain.DiveInformation;
import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.divelog.domain.Review;
import deepdive.backend.divelog.domain.SuitType;
import deepdive.backend.divelog.domain.UnderwaterVisibility;
import deepdive.backend.divelog.domain.WaterType;
import deepdive.backend.divelog.domain.Weather;
import deepdive.backend.divelog.domain.WeightType;
import deepdive.backend.divelog.domain.dto.DiveLogDto;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
    private final EntityManager em;

    public DiveLog convertToDiveLog(DiveLogDto dto) {
        return DiveLog.builder()
            .history(DiveHistory.of(dto.getDate(), dto.getSite(), dto.getPoint(), dto.getBuddy()))
            .review(Review.of(dto.getReviewType(), dto.getComment()))
            .airTankInformation(AirTankInformation.of(dto.getStartPressure(), dto.getEndPressure(),
                dto.getAirTankUsage()))
            .diveInformation(DiveInformation.of(dto.getDepth(), dto.getMin(), dto.getWaterTemp()))
            .airTemp(dto.getAirTemp())
            .weight(dto.getWeight())
            .purpose(Purpose.valueOf(dto.getPurpose()))
            .waterType(WaterType.valueOf(dto.getWaterType()))
            .visibility(UnderwaterVisibility.valueOf(dto.getView()))
            .weather(Weather.valueOf(dto.getWeather()))
            .suitType(SuitType.valueOf(dto.getSuitType()))
            .weightType(WeightType.valueOf(dto.getWeightType()))
            .build();
    }

    @Transactional
    public void save(DiveLogDto dto) {
        Member member = memberService.getByOauthId();

        // TODO : 중복해서 같은 로직을 저장하려 하는 경우 -> 기준은?
        DiveLog diveLog = convertToDiveLog(dto);
        member.addDiveLog(diveLog);
    }

    /**
     * 단권의 diveLog를 조회하는 기능
     * <p>
     * 유저의 ID값을 FK로 갖는 diveLog를 반환합니다.
     *
     * @param diveLogId 조회할 diveLogId
     * @return
     */
    public DiveLogDto showDiveLog(Long diveLogId) {
        DiveLog diveLog = diveLogRepository.findById(diveLogId).orElseThrow(
            ExceptionStatus.NOT_FOUND_LOG::asServiceException);

        return DiveLogDto.of(diveLog);
    }

    /**
     * 지정된 log의 정보를 업데이트 하는 기능
     *
     * @param diveLogId 변경할 log의 id값
     * @param dto       새로 업데이트할 log의 정보들
     */
    @Transactional
    public void updateDiveLog(Long diveLogId, DiveLogDto dto) {
        DiveLog diveLog = diveLogRepository.findById(diveLogId)
            .orElseThrow(ExceptionStatus.NOT_FOUND_LOG::asServiceException);

    }

    /**
     * 유저가 작성한 다이브 로그들을 DTO 형식으로 반환
     * <p>
     * member가 지닌 diveLog들을 pagination 해서 반환
     * <p>
     * 오름차순, 내림차순 정렬
     *
     * @return
     */
    public Page<DiveLogDto> findAllByPagination(Pageable pageable) {
        Member member = memberService.getByOauthId();

        return diveLogRepository.findByMemberId(member.getId(), pageable)
            .map(DiveLogDto::of);
    }


    public Integer getDiveLogCount() {
        Member member = memberService.getByOauthId();

        return member.getDiveLogCount();
    }
}
