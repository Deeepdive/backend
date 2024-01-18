package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.jwt.service.JwtProvider;
import deepdive.backend.member.domain.dto.ProfileRequestDto;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final CacheManager cacheManager;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findByOauthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId)
            .orElseThrow(() -> new IllegalArgumentException("oauthId로 유저를 찾지 못했습니다."));
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

    /**
     * 최초로 등록되는 유저에 관한 로직입니다.
     * <p>
     * refreshToken 발급, member를 db에 등록.
     *
     * @param email cache의 key 역할을 합니다.
     */
    @Transactional
    public void registMember(String email, String provider) {
        AuthUserInfo authUser = AuthUserInfo.of();
        log.info("email = {}", email);

        Member member = Member.of(authUser.getOauthId(), email, provider);
        memberRepository.save(member);

        String refreshToken = tokenProvider.createRefreshToken(member.getOauthId(),
            member.getEmail());
        tokenProvider.createAndSaveRefreshToken(member.getOauthId(), refreshToken);
    }

    @Transactional
    public void updateAgreement(AuthUserInfo authUser, Boolean isAlarmAgree,
        Boolean isMarketingAgree) {
        Member member = findByOauthId(authUser.getOauthId());
        member.updateAgreement(isAlarmAgree, isMarketingAgree);
    }
}
