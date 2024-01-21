package deepdive.backend.profile.service;

import deepdive.backend.commonexception.ExceptionStatus;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.domain.dto.ProfileDto;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberService memberService;
    private final ProfileRepository profileRepository;

    @Transactional
    public void save(ProfileDto dto) {
        validateDuplicateNickName(dto.getNickName());

        Member member = memberService.findByOauthId();
        Profile profile = Profile.of(dto.getNickName(), dto.getPicture(),
            dto.getCertOrganization(),
            dto.getCertType(), dto.getIsTeacher());

        member.setProfile(profile);
    }

    /**
     * 다른 유저와 중복되는 닉네임이 있는지 검증 후 업데이트
     * <p>
     * 기존 프로필의 사진 및 닉네임의 변동사항이 없는지 검증 -> 일단은 DB에 영향 안가서 두는데 이후에 막나?
     *
     * @param dto 유저의 사진, 닉네임이 담긴 프로필
     */
    @Transactional
    public void updateDefaultProfile(ProfileDto dto) {
        validateDuplicateNickName(dto.getNickName());

        Member member = memberService.findByOauthId();
        Profile profile = member.getProfile();

        profile.updateDefaultProfile(dto.getNickName(), dto.getPicture());
        member.setProfile(profile);
    }

    /**
     * 유저의 자격증 관련 프로필을 업데이트합니다.
     *
     * @param dto 발급 기관 정보, 자격증 정보, 강사 정보
     */
    @Transactional
    public void updateDefaultCertProfile(ProfileDto dto) {
        Member member = memberService.findByOauthId();
        Profile profile = member.getProfile();

        profile.updateCertProfile(dto.getCertOrganization(), dto.getCertType(), dto.getIsTeacher());
        member.setProfile(profile);
    }

    private void validateDuplicateNickName(String nickName) {
        Optional<Profile> duplicateNickName = profileRepository.findByNickName(nickName);
        if (duplicateNickName.isPresent()) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }
    }

    public ProfileDto showMemberProfile() {
        Member member = memberService.findByOauthId();
        Profile profile = member.getProfile();

        return ProfileDto.builder()
            .nickName(profile.getNickName())
            .picture(profile.getPicture())
            .build();
    }

    public ProfileDto showCertProfile() {
        Member member = memberService.findByOauthId();
        Profile profile = member.getProfile();

        return ProfileDto.builder()
            .isTeacher(profile.getIsTeacher())
            .certOrganization(profile.getOrganization().name())
            .certType(profile.getCertType().name())
            .build();
    }
}
