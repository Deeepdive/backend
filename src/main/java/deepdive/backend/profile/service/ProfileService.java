package deepdive.backend.profile.service;

import deepdive.backend.dto.profile.ProfileCertRequestDto;
import deepdive.backend.dto.profile.ProfileCertResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import deepdive.backend.dto.profile.ProfileRequestDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.mapper.ProfileMapper;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberService memberService;

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    /**
     * 회원의 프로필(기본 정보, 강사 정보)을 최초로 등록합니다.
     *
     * @param dto
     */
    @Transactional
    public void save(ProfileRequestDto dto) {
        if (isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }

        Profile profile = Profile.of(dto.nickName(), dto.picture(),
            dto.certOrganization(),
            dto.certType(), dto.isTeacher());
        Member member = memberService.getByOauthId();
        member.setProfile(profile);
    }

    public boolean isExistingNickName(String nickName) {

        return profileRepository.findByNickName(nickName)
            .isPresent();
    }

    /**
     * 다른 유저와 중복되는 닉네임이 있는지 검증 후 업데이트
     * <p>
     * 기존 프로필의 사진 및 닉네임의 변동사항이 없는지 검증 -> 일단은 DB에 영향 안가서 두는데 이후에 막나?
     *
     * @param dto 유저의 사진, 닉네임이 담긴 프로필
     */
    @Transactional
    public void updateDefaultProfile(ProfileDefaultDto dto) {

        Member member = memberService.getByOauthId();
        Profile profile = member.getProfile();
        if (isNewNickName(profile.getNickName(), dto.nickName())
            && isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }

        profile.updateDefaultProfile(dto.nickName(), dto.picture());
        member.setProfile(profile);
    }

    /**
     * 유저의 자격증 관련 프로필을 업데이트합니다.
     *
     * @param dto 발급 기관 정보, 자격증 정보, 강사 정보
     */
    @Transactional
    public void updateDefaultCertProfile(ProfileCertRequestDto dto) {
        Profile profile = getByMember(memberService.getByOauthId());

        log.info("dto? = {}", dto);
        profile.updateCertProfile(dto.certOrganization(), dto.certType(), dto.isTeacher());
    }

    public Profile getByMember(Member member) {
        return Optional.ofNullable(member.getProfile())
            .orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
    }

    private void validateDuplicateNickName(String nickName) {
        Optional<Profile> duplicateNickNameProfile = profileRepository.findByNickName(nickName);
        if (duplicateNickNameProfile.isPresent()) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }
    }

    private boolean isNewNickName(String oldNickName, String newNickName) {
        return !newNickName.equals(oldNickName);
    }

    public ProfileDefaultDto showMemberProfile() {
        Profile profile = Optional.ofNullable(memberService.getByOauthId().getProfile())
            .orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);

        return profileMapper.toProfileDefaultDto(profile.getNickName(), profile.getPicture());
    }

    public ProfileCertResponseDto showCertProfile() {
        Profile profile = Optional.ofNullable(memberService.getByOauthId().getProfile())
            .orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);

        return profileMapper.toProfileCertResponseDto(profile.getOrganization(),
            profile.getCertType(),
            profile.getIsTeacher());
    }
}
