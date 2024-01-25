package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.dto.member.MemberRegisterRequestDto;
import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.jwt.service.JwtProvider;
import deepdive.backend.jwt.service.JwtService;
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
    public void registerMember(MemberRegisterRequestDto dto) {
        AuthUserInfo authUser = AuthUserInfo.of(); // 처음 등록한 멤버의 oauthId를 ContextHolder에서 꺼내옵니다.
        String oauthId = authUser.getOauthId();

        // email이 이미 등록되어 있다면, 에러 반환
        // 주어진 DTO의 email, oauthId, provider가 동일하다면 -> 회원이 맞다.
        // db에 올린다
        validateRegistInformation(authUser, dto);

        Member member = Member.defaultInformation(oauthId, dto.email(), dto.provider(),
            dto.isAlarm(), dto.isMarketing());
        memberRepository.save(member);

        String refreshToken = tokenProvider.createRefreshToken(oauthId, member.getEmail());
        jwtService.saveRefreshToken(oauthId, refreshToken);
    }

    private void validateRegistInformation(AuthUserInfo authUser, MemberRegisterRequestDto dto) {
        memberRepository.findByEmail(dto.email())
            .ifPresent(member -> {
                throw ExceptionStatus.DUPLICATE_REGISTER.asServiceException();
            });
        // TODO : provider, oauthID로 추가 검증?
        if (!authUser.getEmail().equals(dto.email())) {
            throw ExceptionStatus.INVALID_REGISTER.asServiceException();
        }
    }

    @Transactional
    public void updateAgreement(Boolean isAlarmAgree, Boolean isMarketingAgree) {
        Member member = getByOauthId();
        member.updateAgreement(isAlarmAgree, isMarketingAgree);
    }
}
