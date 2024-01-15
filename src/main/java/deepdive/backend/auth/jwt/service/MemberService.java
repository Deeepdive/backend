package deepdive.backend.auth.jwt.service;

import deepdive.backend.auth.domain.Member;
import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.auth.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public Member generateMemberByUserProfile(UserProfile userProfile) {
        Member member = Member.of(userProfile);

        memberRepository.save(member);
        return member;
    }

}
