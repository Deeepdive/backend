package deepdive.backend.member.service;

import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import deepdive.backend.profile.domain.entity.Profile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public void updateMemberInfo(Member member, boolean isAlarm, boolean isMarketing, String os) {
        member.updateAgreement(isAlarm, isMarketing, os);
    }

    public void updateProfile(Member member, Profile profile) {
        member.setProfile(profile);
    }

    @Transactional
    public Member save(String email, String provider, String oauthId) {
        Member member = Member.of(email, provider, oauthId);
        return memberRepository.save(member);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
