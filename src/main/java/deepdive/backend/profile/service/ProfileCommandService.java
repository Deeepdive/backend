package deepdive.backend.profile.service;

import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileCommandService {

    private static final String DEFAULT_NICKNAME = "default";
    private static final Integer MAX_LENGTH = 20;
    private static final Integer MAX_DEFAULT_NUMBER_LENGTH = MAX_LENGTH - DEFAULT_NICKNAME.length();
    private final ProfileRepository profileRepository;

    public Profile createDefaultProfile() {
        String defaultNickName = getDefaultNickName();
        return Profile.of(defaultNickName);
    }

    public String getDefaultNickName() {
        String defaultNickName = generateDefaultNickName();

        while (isExistingNickName(defaultNickName)) {
            defaultNickName = generateDefaultNickName();
        }
        return defaultNickName;
    }

    private String generateDefaultNickName() {
        Random random = new Random();
        String randomNumbers = IntStream.range(0, MAX_DEFAULT_NUMBER_LENGTH)
            .mapToObj(i -> String.valueOf(random.nextInt(10)))
            .collect(Collectors.joining());

        return DEFAULT_NICKNAME + randomNumbers;
    }

    public boolean isExistingNickName(String nickName) {

        return profileRepository.findByNickName(nickName)
            .isPresent();
    }
}
