package deepdive.backend.config;

import deepdive.backend.auth.service.CustomOauth2UserService;
import deepdive.backend.auth.service.OauthFailureHandler;
import deepdive.backend.auth.service.OauthSuccessHandler;
import deepdive.backend.jwt.filter.JwtFilter;
import deepdive.backend.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomOauth2UserService customOauth2UserService;
    private final OauthSuccessHandler successHandler;
    private final OauthFailureHandler failureHandler;

    /**
     * kakao 에러 -> ID 관련..
     * <p>
     * 기존 client id로 로그인 시 토큰 발급 성공
     * <p>
     * 지급된 client id -> failureHandler 타는데 이유가 뭐지 redirect ->
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/token/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v1/api-docs/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user.userService(customOauth2UserService))
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            )
            .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
