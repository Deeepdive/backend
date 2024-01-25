package deepdive.backend.divelog.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Getter;

@Embeddable
@Getter
public class DiveHistory {

    private LocalDate date;
    private String site;
    private String point;
    private String buddy;

    public static DiveHistory of(LocalDate date, String site, String point, String buddy) {
        DiveHistory diveHistory = new DiveHistory();
        diveHistory.date = date;
        diveHistory.site = site;
        diveHistory.point = point;
        diveHistory.buddy = buddy;

        return diveHistory;
    }
}
