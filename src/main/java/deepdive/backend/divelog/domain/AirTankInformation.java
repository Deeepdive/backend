package deepdive.backend.divelog.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class AirTankInformation {

    private Long startPressure;
    private Long endPressure;
    // 와우 컬럼명에 usage는 Long을 못쓴다..
    private Long airUsage;

    public static AirTankInformation of(Long startPressure, Long endPressure, Long airUsage) {
        AirTankInformation airTankInformation = new AirTankInformation();
        airTankInformation.startPressure = startPressure;
        airTankInformation.endPressure = endPressure;
        airTankInformation.airUsage = airUsage;

        return airTankInformation;
    }
}
