package com.konkuk.refit.common.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.refit.common.auth.jwt.Jwt;
import com.konkuk.refit.common.auth.jwt.JwtProperties;
import com.konkuk.refit.common.auth.jwt.filter.JwtFilter;
import com.konkuk.refit.common.exception.CustomAccessDeniedHandler;
import com.konkuk.refit.common.exception.CustomAuthEntryPoint;
import com.konkuk.refit.domain.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ObjectMapper objectMapper;
  private final JwtProperties jwtProperties;
  private final MemberService memberService;

  @Bean
  public Jwt jwt() {
    return new Jwt(
        jwtProperties.getClientSecret(),
        jwtProperties.getIssuer(),
        jwtProperties.getTokenExpire(),
        jwtProperties.getRefreshTokenExpire()
    );
  }

  public JwtFilter jwtFilter() {
    return new JwtFilter(jwt(), memberService);
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
        List.of(
            "http://localhost:3000",
            "http://localhost:3001",
            "http://localhost:8080"
        )
    );
    configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    configuration.setAllowedHeaders(
        List.of(
            "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method",
            "Access-Control-Request-Headers", "Authorization", "access_token", "refresh_token",
            "Access-Control-Allow-Origin"
        ));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public CustomAuthEntryPoint authenticationEntryPoint() {
    return new CustomAuthEntryPoint(objectMapper);
  }

  @Bean
  public CustomAccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler(objectMapper);
  }

  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/member/**")
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/**").permitAll()
            .anyRequest().authenticated()
        )
        .headers(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .rememberMe(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
        )
        .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
        .cors(withDefaults());

    return http.build();
    }
}