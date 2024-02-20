package deepdive.backend.member.service;

import static deepdive.backend.member.domain.Provider.APPLE;
import static deepdive.backend.member.domain.Provider.GOOGLE;
import static deepdive.backend.member.domain.Provider.KAKAO;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.Provider;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPolicyService {

    private final MemberRepository memberRepository;

    /**
     * 1. oauthId는 고유 key값이다. 2. email 값도 고유 key값이다.
     *
     * @param oauthId
     */
    public void validatedDuplicateInfo(String oauthId, String providerName) {
        Optional<Member> getMember = memberRepository.findByOauthId(oauthId);
        boolean result = getMember.isPresent();
        Provider provider = Provider.of(providerName);

        if (result && !getMember.get().getProvider().equals(providerName)) {
            if (provider.equals(GOOGLE)) {
                throw ExceptionStatus.DUPLICATE_GOOGLE.asDomainException();
            }
            if (provider.equals(KAKAO)) {
                throw ExceptionStatus.DUPLICATE_KAKAO.asDomainException();
            }
            if (provider.equals(APPLE)) {
                throw ExceptionStatus.DUPLICATE_APPLE.asDomainException();
            }
        }
    }

    public Member validateLoginInfo(String oauthId) {
        return memberRepository.findByOauthId(oauthId)
            .orElseThrow(ExceptionStatus.NOT_FOUND_USER::asServiceException);
    }
}
