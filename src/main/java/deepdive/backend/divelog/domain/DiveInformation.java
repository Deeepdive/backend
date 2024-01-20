package deepdive.backend.divelog.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class DiveInformation {

    private Long depth;
    private Long time;
    private Long waterTemp;
}
