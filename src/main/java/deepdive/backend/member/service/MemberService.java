package deepdive.backend.member.service;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.profile.domain.entity.Profile;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberPolicyService memberPolicyService;


    // oauthId로 찾는다 -> 있으면 provider와 함께 exception 반환
    @Transactional
    public Member registerMember(String email, String provider, String oauthId,
        Boolean isMarketing) {
        Optional<Member> duplicateMember = memberQueryService.findByOauthId(oauthId);
        if (duplicateMember.isPresent()) {
            throw ExceptionStatus.DUPLICATE_REGISTER.asServiceException();
        }
        
        Profile profile = new Profile();
        Member member = Member.of(email, provider, oauthId, isMarketing);
        member.setProfile(profile);

        return memberCommandService.save(member);
    }

    public boolean isRegisteredMember(String oauthId) {
        return memberQueryService.findByOauthId(oauthId).isPresent();
    }

    public Long getValidMemberByLoginInfo(String oauthId, String email) {
        Member member = memberPolicyService.validateLoginInfo(oauthId);

        if (!member.getEmail().equals(email)) {
            throw ExceptionStatus.NOT_FOUND_USER_BY_EMAIL.asServiceException();
        }
        return member.getId();
    }

    @Transactional
    public void delete() {
        Member member = memberQueryService.getMember();
        memberCommandService.delete(member);
    }

}
