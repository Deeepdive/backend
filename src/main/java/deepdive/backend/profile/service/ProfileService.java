package deepdive.backend.profile.service;

import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.domain.dto.ProfileDto;
import deepdive.backend.profile.domain.entity.Profile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    public void update(ProfileDto dto) {

        Member member = memberService.findByOauthId();
        Profile profile = Profile.of(dto.getNickName(), dto.getPicture(),
            dto.getCertOrganization(),
            dto.getCertType(), dto.getIsTeacher());

        member.setProfile(profile);
        memberRepository.save(member);
    }

    public ProfileDto showMemberProfile() {
        Member member = memberService.findByOauthId();
        Profile profile = member.getProfile();

        return ProfileDto.builder()
            .nickName(profile.getNickName())
            .picture(profile.getPicture())
            .build();
    }

    public ProfileDto showCertProfile() {
        Member member = memberService.findByOauthId();
        Profile profile = member.getProfile();

        return ProfileDto.builder()
            .isTeacher(profile.getIsTeacher())
            .certOrganization(profile.getOrganization().name())
            .certType(profile.getCertType().name())
            .build();
    }
}
