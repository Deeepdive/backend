package deepdive.backend.profile.service;

import deepdive.backend.dto.profile.ProfileCertRequestDto;
import deepdive.backend.dto.profile.ProfileCertResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultDto;
import deepdive.backend.dto.profile.ProfileRequestDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.mapper.ProfileMapper;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
    private final ProfilePolicyService profilePolicyService;
    private final ProfileMapper profileMapper;

    /**
     * 회원의 기본 프로필(닉네임, 사진)을 최초로 등록합니다.
     *
     * @param dto 회원의 닉네임, 사진 url
     */
    @Transactional
    public void saveDefaultProfile(ProfileDefaultDto dto) {
        if (isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }

        Profile profile = Profile.defaultProfile(dto.nickName(), dto.picture());
        Member member = memberService.getByOauthId();
        member.setProfile(profile);
    }

    /**
     * 회원의 자격증 관련 프로필을 저장합니다.
     *
     * @param dto 기관명, 자격증명, 강사, 비고
     */
    @Transactional
    public void saveCertProfile(ProfileCertRequestDto dto) {
        CertOrganization organization = CertOrganization.of(dto.certOrganization());
        CertType certType = CertType.of(dto.certType());

        Profile profile = getByMember(memberService.getByOauthId());
        if (organization.equals(CertOrganization.ETC)) {
            if (profilePolicyService.isBlankString(dto.etc())) {
                throw ExceptionStatus.INVALID_CERT_TYPE.asServiceException();
            }
            profile.updateEtcCertProfile(organization, dto.isTeacher(), dto.etc());
            return;
        }
        if (!profilePolicyService.isValidMatchCertProfile(organization, certType)) {
            throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
        }
        profile.updateCertProfile(organization, certType, dto.isTeacher());
    }

    /**
     * 5개 정보를 모두 저장하는 로직
     *
     * @param dto
     */
    @Transactional
    public void saveProfile(ProfileRequestDto dto) {
        if (isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }
        Profile profile = Profile.defaultProfile(dto.nickName(), dto.picture());
        // etc인지 가르는 로직
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

        Profile profile = getByMember(memberService.getByOauthId());
        if (isNewNickName(profile.getNickName(), dto.nickName())
            && isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }

        profile.updateDefaultProfile(dto.nickName(), dto.picture());
    }

    public Profile getByMember(Member member) {
        return Optional.ofNullable(member.getProfile())
            .orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
    }

    private boolean isNewNickName(String oldNickName, String newNickName) {
        return !newNickName.equals(oldNickName);
    }

    public ProfileDefaultDto showMemberProfile() {
        Profile profile = getByMember(memberService.getByOauthId());

        return profileMapper.toProfileDefaultDto(profile.getNickName(), profile.getPicture());
    }

    public ProfileCertResponseDto showCertProfile() {
        Profile profile = getByMember(memberService.getByOauthId());

        return profileMapper.toProfileCertResponseDto(profile.getOrganization(),
            profile.getCertType(),
            profile.getIsTeacher(),
            profile.getEtc());
    }

    public List<ProfileDefaultDto> getBuddiesProfiles(List<Long> buddyIds) {

        return profileRepository.findAllById(buddyIds)
            .stream()
            .map(profile -> profileMapper.toProfileDefaultDto(profile.getNickName(),
                profile.getPicture()))
            .toList();
    }

    public Long getIdByNickName(String nickName) {
        Profile profile = profileRepository.findByNickName(nickName)
            .orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);

        return profile.getId();
    }
}
