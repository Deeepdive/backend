package deepdive.backend.profile.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.dto.profile.ProfileCertResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultImageDto;
import deepdive.backend.dto.profile.ProfileDefaultImageResponseDto;
import deepdive.backend.dto.profile.ProfileDefaultRequestDto;
import deepdive.backend.dto.profile.ProfileDefaultResponseDto;
import deepdive.backend.dto.profile.ProfileResponseDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.mapper.ProfileMapper;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import deepdive.backend.profile.domain.Pictures;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProfileService {


	private final ProfilePolicyService profilePolicyService;
	private final ProfileQueryService profileQueryService;
	private final ProfileMapper profileMapper;

	private final ProfileRepository profileRepository;

	@Transactional
	public void register(Member member) {
		Profile profile = new Profile();
		profile.setMember(member);
		profileRepository.save(profile);
	}

	/**
	 * 회원의 자격증 관련 프로필을 저장합니다.
	 */
	@Transactional
	public void saveCertProfile(CertOrganization organization, CertType type, Boolean isTeacher) {
		if (isTeacher) {
			profilePolicyService.verifyMatchCertProfile(organization, type);
		}

		Profile profile = profileQueryService.getByMemberId();
		profile.updateCertProfile(organization, type, isTeacher);
	}

	@Transactional
	public void saveEtcCertProfile(CertOrganization organization, String etc,
		Boolean isTeacher) {

		Profile profile = profileQueryService.getByMemberId();
		profile.updateEtcCertProfile(organization, isTeacher, etc);
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
		log.info("기본 프로필 검증 시작");
		Long memberId = AuthUserInfo.of().getMemberId();
		Profile profile = profileQueryService.getByMemberId(memberId);

		profilePolicyService.verifyNickName(profile.getNickName(), dto.nickName());
		// TODO: null로 받지 않도록, nickname, url 둘 다 받아서 한번에 UPDATE 하는 로직으로 수정하기
		if (dto.urlNumber() != null) {
			String url = Pictures.getByNumber(dto.urlNumber());
			profile.updateDefaultProfile(url, dto.nickName());
			return;
		}
		profile.updateNickName(dto.nickName());
	}

	public ProfileDefaultResponseDto showDefaultProfile() {
		Profile profile = profileQueryService.getByMemberId();

		return profileMapper.toProfileDefaultResponseDto(profile);
	}

	public ProfileCertResponseDto showCertProfile() {
		Profile profile = profileQueryService.getByMemberId();

		if (profile.getIsTeacher() == null) {
			throw ExceptionStatus.NOT_FOUND_PROFILE.asServiceException();
		}

		return profileMapper.toProfileCertResponseDto(
			profile.getCertOrganization(),
			profile.getCertType(),
			profile.getIsTeacher(),
			profile.getEtc());
	}

	public ProfileDefaultResponseDto getDefaultProfileByNickName(String nickName) {
		Profile profile = profileQueryService.getByNickName(nickName);

		profilePolicyService.verifySelfProfile(profile, profileQueryService.getByMemberId());

		return profileMapper.toProfileDefaultResponseDto(profile);
	}

	public ProfileResponseDto showProfile() {
		Profile profile = profileQueryService.getByMemberId();

		return profileMapper.toProfileResponseDto(profile);
	}

	public ProfileDefaultImageResponseDto getDefaultImages() {
		List<ProfileDefaultImageDto> result = Arrays.stream(Pictures.values())
			.map(picture ->
				profileMapper.toProfileDefaultImageDto(picture.ordinal() + 1, picture.getUrl()))
			.toList();

		return new ProfileDefaultImageResponseDto(result);
	}
}
