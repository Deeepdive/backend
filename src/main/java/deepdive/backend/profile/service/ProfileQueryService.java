package deepdive.backend.profile.service;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileQueryService {

    private final ProfileRepository profileRepository;

    public List<Profile> getProfiles(List<Long> ids) {
        return profileRepository.findAllById(ids);
    }

    public List<String> getProfileNames(List<Profile> profiles) {
        return profileRepository.findNickNames(profiles);
    }

    public Profile getByNickName(String nickName) {
        return profileRepository.findByNickName(nickName)
            .orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
    }
}
