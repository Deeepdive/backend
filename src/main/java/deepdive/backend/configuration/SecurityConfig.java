package deepdive.backend.configuration;

import deepdive.backend.auth.service.CustomOauth2UserService;
import deepdive.backend.auth.service.OauthFailureHandler;
import deepdive.backend.auth.service.OauthSuccessHandler;
import deepdive.backend.exception.CustomAccessDenialHandler;
import deepdive.backend.jwt.filter.JwtFilter;
import deepdive.backend.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomOauth2UserService customOauth2UserService;
    private final OauthSuccessHandler successHandler;
    private final OauthFailureHandler failureHandler;
    private final CustomAccessDenialHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/token").permitAll()
                .requestMatchers("/login/**").permitAll() // OAuth2.0 EndPoint 요청은 인증을 하지 않는다
                .requestMatchers("/oauth2/**").permitAll()
                .requestMatchers("/status").permitAll() // 로드밸런서 상태 확인용
                .requestMatchers(AuthenticateMatchers.swaggerArray).permitAll()
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user.userService(customOauth2UserService))
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            )
            .exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND)))
            .logout(logout -> logout.clearAuthentication(true))
            .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
