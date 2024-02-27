package deepdive.backend.member.service;

import deepdive.backend.divelog.repository.DiveLogProfileRepository;
import deepdive.backend.divelog.repository.DiveLogRepository;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.Provider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.profile.domain.entity.Profile;
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

	private final DiveLogRepository diveLogRepository;
	private final DiveLogProfileRepository diveLogProfileRepository;

	// oauthId로 찾는다 -> 있으면 provider와 함께 exception 반환
	@Transactional
	public Member registerMember(String email, String provider, String oauthId,
		Boolean isMarketing) {
		memberPolicyService.validateRegisterInfo(oauthId);

		Provider.of(provider);
		Profile profile = new Profile();
		Member member = Member.of(email, provider, oauthId, isMarketing);
		member.setProfile(profile);

		return memberCommandService.save(member);
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
		Member member = memberQueryService.getMember();

		Profile profile = member.getProfile();
		diveLogProfileRepository.deleteAllByProfile(profile);
		diveLogRepository.deleteAll(member.getDiveLogs());
		memberCommandService.delete(member);
	}

}
