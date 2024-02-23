package deepdive.backend.dto.profile;

import java.util.List;

public record ProfileDefaultImageResponseDto(
	List<ProfileDefaultImageDto> defaultPictures
) {

}
