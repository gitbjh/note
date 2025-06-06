package com.example.spring.basicboardv2.config;

import com.example.spring.basicboardv2.config.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/static/**",
                        "/css/**",
                        "/js/**"
                ); // 정적 리소스 경로 무시
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        // 로그인 정보를 cookie에 담기 위해서 session을 비활성화
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        auth -> auth
//                                .requestMatchers("/api/board/**").hasRole("ADMIN") > security에 인가 시키는 방법
                                .requestMatchers(
                                        // 화면 이동
                                        new AntPathRequestMatcher("/", GET.name()),
                                        new AntPathRequestMatcher("/member/join", GET.name()),
                                        new AntPathRequestMatcher("/member/login", GET.name()),
                                        new AntPathRequestMatcher("/write", GET.name()),
                                        new AntPathRequestMatcher("/detail", GET.name()),
                                        new AntPathRequestMatcher("/update/*", GET.name()),
                                        new AntPathRequestMatcher("/access-denied", GET.name()),
                                        // 기능
                                        new AntPathRequestMatcher("/refresh-token", POST.name()),
                                        new AntPathRequestMatcher("/join", POST.name()),
                                        new AntPathRequestMatcher("/login", POST.name()),
                                        new AntPathRequestMatcher("/logout", POST.name()),
                                        new AntPathRequestMatcher("/api/board/file/download/*", GET.name())
                                )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .logout(AbstractHttpConfigurer::disable)
                // JWT 필터 추가, 시큐리티의 인증 필터보다 앞에 설정해서 로그인 전에 인증을 더 할 필요없게 만듬
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    @Bean // 인증을 진행하는 manager 반환 > 인증을 진행한다 = 로그인 한다
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/access-denied");
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/access-denied");
        };
    }

}
