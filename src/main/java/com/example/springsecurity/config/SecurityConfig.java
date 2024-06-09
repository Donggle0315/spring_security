package com.example.springsecurity.config;

import com.example.springsecurity.OAuth2.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //해당 클래스가 spring security에 의해 관리됨
public class SecurityConfig {
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/loginProc", "/join", "/joinProc").permitAll() //해당 경로는 모두가 접근 가능
                        .requestMatchers("/").hasRole("USER") //해당 역할을 가져야만 접근 가능
                        .requestMatchers("/manager").hasAnyRole("MANAGER")
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("USER") //여러 역할 중 하나만 가지더라도 접근 간으
                        .anyRequest().authenticated() //나머지 경로에 대해서는 인증을 받아야 함
                );
        //formLogin
//        http
//                .formLogin((auth) -> auth.loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll()
//                );
//        httpBasic
        http
                .httpBasic(Customizer.withDefaults()
                );

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );

//        http
//                .csrf((auth) -> auth.disable());

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n" +
                "ROLE_MANAGER > ROLE_USER");

        return hierarchy;
    }
}
