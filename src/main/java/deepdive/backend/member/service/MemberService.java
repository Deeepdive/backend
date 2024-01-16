package deepdive.backend.member.service;

import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.member.domain.Member;
import deepdive.backend.member.repository.MemberRepository;
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

    public Optional<Member> findByOauthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId);
    }

    @Transactional
    public Member registerMember(String oauthId, String email, UserProfile userProfile) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            return memberRepository.save(Member.of(userProfile));
        }
        if (!oauthId.equals(member.get().getOauthId())) {
            throw new IllegalArgumentException("중복 로그인 시도");
        }
        return member.get();
    }

}
