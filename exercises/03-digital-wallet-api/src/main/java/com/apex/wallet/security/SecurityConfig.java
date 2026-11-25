package com.apex.wallet.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/auth/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**", "/actuator/health")
                            .permitAll();
                    req.anyRequest().authenticated();
                })
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint((req, res, e)->{
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/problem+json");
                            res.getWriter().write("""
                                    {
                                    "type" : "about:blank",
                                    "title" : "Unauthorized",
                                    "status" : 401,
                                    "detail" : "Authentication required or token invalid"
                                    }
                                    """);
                        })
                        .accessDeniedHandler((req,res,e) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/problem+json");
                            res.getWriter().write("""
                                    {"type":"about:blank","title":"Forbidden","status":403,
                                     "detail":"Insufficient permissions"}""");
                        }))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }
}
