package deepdive.backend.divelog.domain.dto;

import deepdive.backend.divelog.domain.DiveHistory;
import deepdive.backend.divelog.domain.entity.DiveLog;
import java.time.LocalDate;
import lombok.Data;

@Data
public class DiveLogResponseDto {

    /**
     * 보여줘야되는 값
     * <p>
     * 총 로그 개수, 멤버가 지니고 있는 다이브 로그들 -> 다이브 날짜, 위치, 포인트 지점명, 다이빙 센터 이름, 버디, 다이브 목적(펀, 교육)
     */
    private LocalDate diveDate;
    private String point;
    private String buddy;
    private String site;
    private String purpose;

    public DiveLogResponseDto of(DiveLog diveLog) {
        DiveLogResponseDto dto = new DiveLogResponseDto();
        DiveHistory diveHistory = diveLog.getHistory();

        dto.diveDate = diveHistory.getDate();
        dto.point = diveHistory.getPoint();
        dto.buddy = diveHistory.getBuddy();
        dto.purpose = diveLog.getPurpose().name();
        dto.site = diveHistory.getSite();

        return dto;
    }
}
