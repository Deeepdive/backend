package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.commonexception.ExceptionStatus;
import deepdive.backend.jwt.service.JwtProvider;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.dto.RegisterMemberDto;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import deepdive.backend.profile.domain.dto.ProfileRequestDto;
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

    /**
     * 멤버의 자격증 관련 프로필을 업데이트
     *
     * @param requestDto 자격증 발급단체, 유형, 강사인지
     * @return
     */
    @Transactional
    public void updateCertInformation(AuthUserInfo authUser, ProfileRequestDto requestDto) {
        Member member = findByOauthId(authUser.getOauthId());

        member.updateCertInformation(requestDto.getCertOrganization(),
            requestDto.getCertType(), requestDto.getIsTeacher());
    }

    @Transactional
    public void updateProfile(AuthUserInfo authUser, ProfileRequestDto requestDto) {
        Member member = findByOauthId(authUser.getOauthId());

        member.updateProfile(requestDto.getNickName(), requestDto.getProfile());
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
