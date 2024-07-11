package com.master.BioskopVozdovac.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity //for @Pre/Post Authorize and @Pre/Post filter
public class SecurityConfig {

    private final MemberAuthenticationEntryPoint entryPoint;
    private final MemberAuthenticationProvider provider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(entryPoint))
                .addFilterBefore(new JwtAuthFIlter(provider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests.
                        requestMatchers(HttpMethod.POST, "/api/members/register", "/api/members/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/movie/allWithAWS", "/api/role/**", "/api/projection/today").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

}
