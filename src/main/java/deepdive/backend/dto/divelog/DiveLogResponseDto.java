package deepdive.backend.dto.divelog;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import deepdive.backend.divelog.domain.DiveHistory;
import deepdive.backend.divelog.domain.Purpose;

/**
 * 유저가 작성한 여러 개의 diveLog 들에 대한 정보들을 담을 ResponseDTO
 *
 * @param id
 * @param diveHistory -> dive 날짜, site, point, buddy
 * @param purpose
 */
public record DiveLogResponseDto(Long id,
                                 @JsonUnwrapped DiveHistory diveHistory,
                                 Purpose purpose) {

}
