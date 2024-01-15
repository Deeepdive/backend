package deepdive.backend.config;

import deepdive.backend.auth.jwt.filter.JwtFilter;
import deepdive.backend.auth.jwt.service.JwtService;
import deepdive.backend.auth.service.CustomOauth2UserService;
import deepdive.backend.auth.service.OauthFailureHandler;
import deepdive.backend.auth.service.OauthSuccessHandler;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/token/**").permitAll()
                .requestMatchers("/oauth2/*").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user.userService(customOauth2UserService))
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            )
            .addFilterBefore(
                new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
