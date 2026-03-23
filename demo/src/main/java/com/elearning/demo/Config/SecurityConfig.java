package com.elearning.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public SpringSessionRememberMeServices rememberMeServices() {
//        SpringSessionRememberMeServices rememberMeServices =
//                new SpringSessionRememberMeServices();
//        // optionally customize
//        rememberMeServices.setAlwaysRemember(true);
//        return rememberMeServices;
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF: demo thì disable, đi làm nên bật
                .csrf(csrf -> csrf.disable())

                // SESSION
                .sessionManagement(session -> session
                        .maximumSessions(1) // Limit to 1 concurrent session per user
                        .maxSessionsPreventsLogin(true) // If true, prevents new login; if false, expires oldest session
                        .expiredUrl("/login?expired") // URL to redirect to if a session is expired
                )

                // AUTHORIZATION
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "api/v1/**").permitAll()
                        .anyRequest().authenticated()
                )

                // FORM LOGIN → TẠO SESSION
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler(UserSuccessHandler())
                        .permitAll()
                )
//                .rememberMe((rememberMe) -> rememberMe
//                        .rememberMeServices(rememberMeServices())
//                )

                // LOGOUT → XOÁ SESSION DB
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
    @Bean
    public AuthenticationSuccessHandler UserSuccessHandler() {
        return new UserSuccessHandler();
    }

    @Bean(name = "sessionRegistry")
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}