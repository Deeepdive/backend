package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.dto.DiveLogResponseDto;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.member.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveLogService {

    private final DiveLogRepository diveLogRepository;
    private final MemberService memberService;
    private final EntityManager em;

    @Transactional
    public DiveLog save(DiveLog diveLog) {
        return diveLogRepository.save(diveLog);
    }

    //    /**
//     * contextHolder에서 유저 정보를 찾고, divelog를 저장합니다.
//     *
//     * @param requestDto 저장해야되는 정보들
//     * @param authUser   security를 통해 인가를 받은 user 정보
//     * @return
//     */
//    @Transactional
//    public DiveLogResponseDto saveDiveLog(DiveLogRequestDto requestDto, AuthUserInfo authUser) {
//        String oauthId = authUser.getOauthId();
//        Member member = memberService.findByOauthId(oauthId);
//    }
//
//    @Transactional
//    public DiveLogResponseDto updateDiveLog(DiveLogRequestDto requestDto, AuthUserInfo authUser) {
//
//    }
    public DiveLog findById(Long logId) {
        return diveLogRepository.findById(logId)
            .orElseThrow(IllegalAccessError::new);
    }

    public List<DiveLogResponseDto> findLogDtos() {
        return em.createQuery(
            "select new deepdive.backend.divelog.domain.dto.DiveLogResponseDto(dl.dieveDate, dl.point, dl.buddy, dl.site, dl.purpose)"
                + " from DiveLog dl", DiveLogResponseDto.class
        ).getResultList();
    }
}
