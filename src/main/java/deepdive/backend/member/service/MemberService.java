package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.dto.member.MemberRegisterRequestDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member getMember() {
        return getById(getMemberId());
    }

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
    }

    public Member getByOauthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId)
            .orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
    }

    @Transactional
    public Member save(String email, String provider, String oauthId) {
        Member member = Member.of(email, provider, oauthId);

        return memberRepository.save(member);
    }

    @Transactional
    public void registerMember(MemberRegisterRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.email())
            .orElseThrow(ExceptionStatus.INVALID_REGISTER::asServiceException);

        member.updateAgreement(dto.isAlarm(), dto.isMarketing());
    }

    public Long getMemberId() {
        return AuthUserInfo.of().getMemberId();
    }
}
