package deepdive.backend.divelog.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class DiveInformation {

    private Long depth;
    private Long time;
    private Long waterTemp;

    public static DiveInformation of(Long depth, Long min, Long waterTemp) {
        DiveInformation diveInformation = new DiveInformation();
        diveInformation.depth = depth;
        diveInformation.time = min;
        diveInformation.waterTemp = waterTemp;

        return diveInformation;
    }
}
