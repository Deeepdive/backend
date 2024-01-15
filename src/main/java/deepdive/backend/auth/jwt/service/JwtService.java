package deepdive.backend.auth.jwt.service;

import deepdive.backend.auth.domain.AuthUserInfo;
import deepdive.backend.auth.domain.JwtToken;
import deepdive.backend.auth.domain.Member;
import deepdive.backend.auth.domain.ReIssueDto;
import deepdive.backend.auth.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository tokenRepository;
    private final MemberService memberService;
    @Value("${jwt.token.secret}")
    private String secret_code;
    @Value("${refreshtoken.expires}")
    private Long refreshTokenExpiration;
    @Value("${accesstoken.expires}")
    private Long accessTokenExpiration;
    @Value("${issuer}")
    private String issuer;

    @Transactional
    public void create(Long memberId, String refreshToken) {
        tokenRepository.save(new JwtToken(memberId, refreshToken));
    }

    @Transactional
    public void updateRefreshToken(Long memberId, String email) {
        JwtToken refreshToken = tokenRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        log.info("member ID : {}", refreshToken.getMemberId());
        String createdRefreshToken = createRefreshToken(memberId, email);

        refreshToken.updateRefreshToken(createdRefreshToken);
    }

    public String reissueAccessToken(ReIssueDto reIssueDto) {
        JwtToken memberToken = tokenRepository.findByMemberId(reIssueDto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // TODO : 추후 service 분리
        memberToken.validateRefreshToken(reIssueDto.getRefreshToken());

        return createAccessToken(reIssueDto.getMemberId(), "name");
    }

    private String createRefreshToken(Long memberId, String email) {
        return createToken(memberId, email, refreshTokenExpiration);
    }

    public String createAccessToken(Long memberId, String email) {
        return createToken(memberId, email, accessTokenExpiration);
    }

    private String createToken(Long memberId, String email, Long expireTime) {
        Claims claims = Jwts.claims().setSubject("User");
        claims.put("memberId", memberId);
        claims.put("email", email);
        claims.put("roles", "User");
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setIssuer(issuer)
            .setExpiration(new Date(now.getTime() + expireTime))
            .signWith(generateSecretKey(secret_code), SignatureAlgorithm.HS256)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        log.info("token info: {}", claims);

        if (claims.get("roles") == null) {
            throw new IllegalArgumentException("권한 정보가 존재하지 않습니다.");
        }

        List<SimpleGrantedAuthority> authorities = Stream.of(claims.get("role").toString())
            .map(SimpleGrantedAuthority::new)
            .toList();

        Long memberId = claims.get("memberId", Long.class);
        Member member = memberService.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // principal 구축
        AuthUserInfo authUserInfo = AuthUserInfo.builder()
            .memberId(memberId)
            .email(member.getEmail())
            .profile(member.getPicture())
            .build();

        return new UsernamePasswordAuthenticationToken(authUserInfo, "", authorities);
    }

    private Claims parseToken(String accessToken) {
        log.info("토큰이 존재하나여.. : {}", accessToken);

        try {
            return Jwts.parserBuilder()
                .setSigningKey(generateSecretKey(secret_code))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (MalformedJwtException e) {
            // TODO : 추후에 CustomException 구축
            throw new JwtException("잘못된 형식의 토큰입니다");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원하지 않는 형식의 토큰입니다");
        } catch (IllegalArgumentException e) {
            throw new JwtException("잘못된 정보입니다.");
        } catch (SignatureException e) {
            throw new JwtException("시그니쳐가 이상한데요");
        }
    }

    private Key generateSecretKey(String secretCode) {
        String encodeSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodeSecretCode.getBytes());
    }
}
