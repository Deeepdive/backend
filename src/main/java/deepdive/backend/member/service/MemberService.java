package deepdive.backend.member.service;

import deepdive.backend.dto.member.MemberRegisterRequestDto;
import deepdive.backend.member.domain.Os;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.service.ProfileCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    private final ProfileCommandService profileCommandService;

    @Transactional
    public void registerMember(MemberRegisterRequestDto dto) {
        Member member = memberQueryService.getByEmail(dto.email());
//        Profile profile = profileCommandService.createDefaultProfile();
        Profile profile = new Profile();
        Os os = Os.of(dto.os());

        memberCommandService.updateMemberInfo(member, dto.isAlarm(), dto.isMarketing(), os);
        memberCommandService.updateProfile(member, profile);
    }

    @Transactional
    public void delete() {
        Member member = memberQueryService.getMember();
        memberCommandService.delete(member);
    }

}
