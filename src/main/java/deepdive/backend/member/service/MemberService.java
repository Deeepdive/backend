package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.commonexception.ExceptionStatus;
import deepdive.backend.jwt.service.JwtProvider;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.dto.RegisterMemberDto;
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

    private final JwtProvider tokenProvider;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findByOauthId(String oauthId) {
        Optional<Member> member = memberRepository.findByOauthId(oauthId);
        if (member.isEmpty()) {
            throw ExceptionStatus.NOT_FOUND_USER.asServiceException();
        }

        return member.get();
    }

    public Member findByOauthId() {
        AuthUserInfo authUser = AuthUserInfo.of();

        return memberRepository.findByOauthId(authUser.getOauthId())
            .orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
    }

    @Transactional
    public void registerMember(RegisterMemberDto dto) {
        AuthUserInfo authUser = AuthUserInfo.of(); // 처음 등록한 멤버의 oauthId를 ContextHolder에서 꺼내옵니다.
        String oauthId = authUser.getOauthId();

        memberRepository.findByOauthId(authUser.getOauthId())
            .ifPresent(member -> {
                throw ExceptionStatus.DUPLICATE_REGISTER.asServiceException();
            });

        Member member = Member.defaultInformation(oauthId, dto.getEmail(), dto.getProvider(),
            dto.getIsAlarm(), dto.getIsMarketing());
        memberRepository.save(member);

        String refreshToken = tokenProvider.createRefreshToken(oauthId, member.getEmail());
        jwtService.saveRefreshToken(oauthId, refreshToken);
    }

    @Transactional
    public void updateAgreement(AuthUserInfo authUser, Boolean isAlarmAgree,
        Boolean isMarketingAgree) {
        Member member = findByOauthId(authUser.getOauthId());
        member.updateAgreement(isAlarmAgree, isMarketingAgree);
    }
}
