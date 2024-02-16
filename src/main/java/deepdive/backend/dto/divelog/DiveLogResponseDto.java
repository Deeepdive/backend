package deepdive.backend.dto.divelog;

import deepdive.backend.divelog.domain.Purpose;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import java.time.LocalDate;
import java.util.List;

/**
 * 유저가 작성한 여러 개의 diveLog 들에 대한 정보들을 담을 ResponseDTO
 *
 * @param id
 * @param diveHistory   -> dive 날짜, site, point, buddy
 * @param purpose
 * @param buddyProfiles -> 여러 버디들의 defaultProfile, 총 개수
 */
public record DiveLogResponseDto(
    Long id,
    LocalDate diveDate,
    String center,
    String point,
    Purpose purpose,
    List<ProfileDefaultDto> buddyProfiles) {

}
