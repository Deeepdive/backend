package deepdive.backend.member.service;

import deepdive.backend.member.domain.Provider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

	private final MemberRepository memberRepository;

	public void delete(Member member) {
		memberRepository.delete(member);
	}

	@Transactional
	public Member saveByOauthInfo(String email, Provider provider, String oauthId) {
		Member member = Member.oauthInfo(email, provider, oauthId);

		return memberRepository.save(member);
	}
}
