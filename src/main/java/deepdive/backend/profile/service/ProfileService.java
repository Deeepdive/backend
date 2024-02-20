package deepdive.backend.profile.service;

import deepdive.backend.dto.profile.ProfileCertRequestDto;
import deepdive.backend.dto.profile.ProfileCertResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultImageDto;
import deepdive.backend.dto.profile.ProfileDefaultRequestDto;
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import deepdive.backend.dto.profile.ProfileRequestDto;
import deepdive.backend.dto.profile.ProfileResponseDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.mapper.ProfileMapper;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.service.MemberQueryService;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import deepdive.backend.profile.domain.Pictures;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {


    private final MemberQueryService memberQueryService;

    private final ProfileRepository profileRepository;
    private final ProfilePolicyService profilePolicyService;
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;
    private final ProfileMapper profileMapper;

    /**
     * 회원의 기본 프로필(닉네임, 사진)을 최초로 등록합니다.
     *
     * @param dto 회원의 닉네임, 사진 url
     */
    @Transactional
    public void saveDefaultProfile(ProfileDefaultRequestDto dto) {
        String url = Pictures.getByNumber(dto.urlNumber());
        if (isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }

        Member member = memberQueryService.getMember();
        Profile profile = Profile.defaultProfile(dto.nickName(), url);

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
        Profile profile = getByMember();

        if (organization.equals(CertOrganization.ETC)) {
            if (profilePolicyService.isBlankString(dto.etc())) {
                throw ExceptionStatus.INVALID_CERT_TYPE.asServiceException();
            }
            profile.updateEtcCertProfile(organization, dto.isTeacher(), dto.etc());
            return;
        }

        CertType certType = CertType.of(dto.certType());
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
    public void updateProfile(ProfileRequestDto dto) {
        String url = Pictures.getByNumber(dto.pictureNumber());
        CertOrganization organization = CertOrganization.of(dto.certOrganization());
        if (!validateNickName(dto.nickName())) {
            throw ExceptionStatus.INVALID_NICKNAME.asServiceException();
        }
        Profile profile = getByMember();

        if (organization.equals(CertOrganization.ETC)) {
            if (profilePolicyService.isBlankString(dto.etc())) {
                throw ExceptionStatus.INVALID_CERT_TYPE.asServiceException();
            }
            profileCommandService.updateEtcProfile(profile, dto.nickName(), url,
                organization, dto.isTeacher(), dto.etc());
            return;
        }
        CertType certType = CertType.of(dto.certType());
        if (!profilePolicyService.isValidMatchCertProfile(organization, certType)) {
            throw ExceptionStatus.INVALID_MATCH_PROFILE.asServiceException();
        }
        if (isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }

        profileCommandService.updateCommonProfile(profile,
            dto.nickName(), url,
            organization, certType, dto.isTeacher());
    }

    private boolean validateNickName(String nickName) {
        if (nickName.length() > 20) {
            throw ExceptionStatus.INVALID_NICKNAME.asServiceException();
        }
        String regex = "^[a-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(nickName).matches();
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
    public void updateDefaultProfile(ProfileDefaultRequestDto dto) {
        String url = Pictures.getByNumber(dto.urlNumber());
        Profile profile = getByMember();

        if (isNewNickName(profile.getNickName(), dto.nickName())
            && isExistingNickName(dto.nickName())) {
            throw ExceptionStatus.DUPLICATE_NICKNAME.asServiceException();
        }
        profile.updateDefaultProfile(dto.nickName(), url);
    }

    public Profile getByMember() {
        return memberQueryService.getMember().getProfile();
    }

    private boolean isNewNickName(String oldNickName, String newNickName) {
        return !newNickName.equals(oldNickName);
    }

    public ProfileDefaultResponseDto showDefaultProfile() {
        Profile profile = getByMember();

        return profileMapper.toProfileDefaultResponseDto(profile.getId(), profile.getNickName(),
            profile.getPicture());
    }

    public ProfileCertResponseDto showCertProfile() {
        Profile profile = getByMember();

        if (profile.getIsTeacher() == null) {
            throw ExceptionStatus.NOT_FOUND_PROFILE.asServiceException();
        }

        return profileMapper.toProfileCertResponseDto(
            profile.getOrganization(),
            profile.getCertType(),
            profile.getIsTeacher(),
            profile.getEtc());
    }

    public List<ProfileDefaultResponseDto> getBuddiesProfiles(List<Profile> profiles) {
        return profiles.stream()
            .map(profile ->
                profileMapper.toProfileDefaultResponseDto(
                    profile.getId(),
                    profile.getNickName(),
                    profile.getPicture())
            )
            .toList();
    }

    public ProfileDefaultResponseDto getIdByNickName(String nickName) {
        Profile profile = profileQueryService.getByNickName(nickName);
        if (profile.equals(getByMember())) {
            throw ExceptionStatus.INVALID_BUDDY_PROFILE.asServiceException();
        }

        return profileMapper.toProfileDefaultResponseDto(profile.getId(), profile.getNickName(),
            profile.getPicture());
    }

    public ProfileResponseDto showProfile() {
        Profile profile = getByMember();

        return profileMapper.toProfileResponseDto(profile);
    }

    public List<ProfileDefaultImageDto> getDefaultImages() {
        return Arrays.stream(Pictures.values())
            .map(picture ->
                profileMapper.toProfileDefaultImageDto(picture.ordinal() + 1, picture.getUrl()))
            .toList();
    }
}
