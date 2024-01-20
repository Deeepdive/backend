package deepdive.backend.profile.service;

import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import deepdive.backend.member.service.MemberService;
import deepdive.backend.profile.domain.dto.ProfileRequestDto;
import deepdive.backend.profile.domain.entity.Profile;
import deepdive.backend.profile.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    public void update(ProfileRequestDto dto) {

        Member member = memberService.findByOauthId();
        Profile profile = Profile.of(dto.getNickName(), dto.getPicture(),
            dto.getCertOrganization(),
            dto.getCertType(), dto.getIsTeacher());

        member.setProfile(profile);
        memberRepository.save(member);
    }
}
