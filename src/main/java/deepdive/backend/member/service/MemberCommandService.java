package deepdive.backend.member.service;

import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Transactional
    public Member saveByOauthInfo(String email, String provider, String oauthId) {
        Member member = Member.oauthInfo(email, provider, oauthId);

        return memberRepository.save(member);
    }
}
