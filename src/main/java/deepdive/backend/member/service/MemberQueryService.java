package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

	private final MemberRepository memberRepository;

	public Long getMemberId() {
		return AuthUserInfo.of().getMemberId();
	}

	public Member getMember() {
		return memberRepository.findById(getMemberId())
			.orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
	}

	public Member getMemberWithProfile() {
		Long memberId = getMemberId();

		return memberRepository.findByIdWithProfile(memberId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_PROFILE::asServiceException);
	}

	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	public Member getByOauthId(String oauthId) {
		return memberRepository.findByOauthId(oauthId)
			.orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
	}

	public Optional<Member> findByOauthId(String oauthId) {
		return memberRepository.findByOauthId(oauthId);
	}


}
