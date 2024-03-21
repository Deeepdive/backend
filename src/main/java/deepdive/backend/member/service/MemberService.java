package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.divelog.repository.DiveLogProfileRepository;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.Provider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import deepdive.backend.profile.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;
	private final MemberPolicyService memberPolicyService;
	private final MemberRepository memberRepository;

	private final ProfileService profileService;

	private final DiveLogRepository diveLogRepository;
	private final DiveLogProfileRepository diveLogProfileRepository;

	/**
	 * @param email
	 * @param provider
	 * @param oauthId
	 * @param isMarketing
	 * @return
	 */
	@Transactional
	public Member registerMember(String email, Provider provider, String oauthId,
		Boolean isMarketing) {
		memberPolicyService.validateRegisterInfo(oauthId);

		Member member = Member.of(email, provider, oauthId, isMarketing);
		Member saved = memberRepository.save(member);
		profileService.register(member);

		return saved;
	}

	public boolean isRegisteredMember(String oauthId) {
		return memberQueryService.findByOauthId(oauthId).isPresent();
	}

	public Long getValidMemberByLoginInfo(String oauthId, String email) {
		Member member = memberQueryService.getByOauthId(oauthId);

		if (!member.getEmail().equals(email)) {
			throw ExceptionStatus.NOT_FOUND_USER_BY_EMAIL.asServiceException();
		}
		return member.getId();
	}

	/**
	 * diveLogProfile 삭제 후 diveLog 삭제, 이후 Member 삭제
	 */
	@Transactional
	public void delete() {
		Long memberId = AuthUserInfo.of().getMemberId();

		Member member = memberRepository.findByIdWithProfile(memberId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);

		diveLogProfileRepository.deleteAllByProfile(member.getProfile());
		diveLogRepository.deleteAll(member.getDiveLogs());
		memberCommandService.delete(member);
	}

}
