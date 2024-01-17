package deepdive.backend.member.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.auth.domain.UserProfile;
import deepdive.backend.divelog.domain.entity.DiveLog;
import deepdive.backend.jwt.service.JwtService;
import deepdive.backend.member.domain.dto.ProfileRequestDto;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtService jwtService;
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

    @Transactional
    public Member registerMember(String oauthId, String email, UserProfile userProfile) {
        Optional<Member> member = memberRepository.findByEmail(email);

        // 아예 회원가입 한적 없음 -> refresh 토큰을 저장해야줘야한다.
        if (member.isEmpty()) {
            return memberRepository.save(Member.defaultInformation(userProfile));
        }
        if (!oauthId.equals(member.get().getOauthId())) {
            throw new IllegalArgumentException("중복 이메일로 로그인 시도");
        }
        return member.get();
    }

    /**
     * 최초로 등록되는 유저에 관한 로직입니다.
     * <p>
     * refreshToken 발급, member를 db에 등록.
     *
     * @param email cache의 key 역할을 합니다.
     */
    @Transactional
    public void registMember(String email) {
        Cache userProfile = cacheManager.getCache("userProfile");
        UserProfile userInformation = userProfile.get(email, UserProfile.class);

        Member member = Member.defaultInformation(userInformation);
        memberRepository.save(member);

        String refreshToken = jwtService.createRefreshToken(member.getOauthId(), member.getEmail());
        jwtService.createAndSaveRefreshToken(member.getOauthId(), refreshToken);
    }

    public List<DiveLog> findAllDiveLogs() {
        return null;
    }

    @Transactional
    public void updateAgreement(AuthUserInfo authUser, Boolean isAlarmAgree,
        Boolean isMarketingAgree) {
        Member member = findByOauthId(authUser.getOauthId());
        member.updateAgreement(isAlarmAgree, isMarketingAgree);
    }
}
