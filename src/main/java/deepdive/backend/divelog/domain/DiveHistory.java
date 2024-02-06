package deepdive.backend.divelog.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Embeddable
@Getter
public class DiveHistory {

    private LocalDate date;
    private String site;
    private String point;
    //    private String buddies; // List<Long> 타입으로 profile ID들 받는 걸로 변경
    private List<Long> buddyIds = new ArrayList<>();

    public static DiveHistory of(LocalDate date, String site, String point, List<Long> buddyIds) {
        DiveHistory diveHistory = new DiveHistory();
        diveHistory.date = date;
        diveHistory.site = site;
        diveHistory.point = point;
        diveHistory.buddyIds = buddyIds;

        return diveHistory;
    }
}
