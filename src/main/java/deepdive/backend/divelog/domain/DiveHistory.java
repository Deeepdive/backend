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
}
