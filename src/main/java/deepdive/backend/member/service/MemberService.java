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

    public Member getByOauthId() {
        AuthUserInfo authUser = AuthUserInfo.of();

        return memberRepository.findByOauthId(authUser.getOauthId())
            .orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
    }

    @Transactional
    public void registerMember(RegisterMemberDto dto) {
        AuthUserInfo authUser = AuthUserInfo.of(); // 처음 등록한 멤버의 oauthId를 ContextHolder에서 꺼내옵니다.
        String oauthId = authUser.getOauthId();

        // 추가 검증 -> dto의 이메일과 DB 내부의 email이 동일하지 않다면 error 반환.
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
        Member member = getByOauthId();
        member.updateAgreement(isAlarmAgree, isMarketingAgree);
    }
}
