package com.konkuk.refit.common.auth.jwt.filter;

import com.konkuk.refit.common.auth.jwt.Jwt;
import com.konkuk.refit.domain.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final Jwt jwt;
  private final MemberService memberService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      String token = getAccessToken(request);

      if (token != null) {
        try{Jwt.Claims claims = verify(token);

          Long memberId = claims.getMemberId();


          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
              new UsernamePasswordAuthenticationToken(memberId, null);
          SecurityContextHolder.getContext()
              .setAuthentication(usernamePasswordAuthenticationToken);
        }catch (Exception e) {
          log.warn("사용자 인증토큰 처리중 문제가 발생하였습니다 : {}", e.getMessage());
        }
      } else {
        log.debug("사용자 인증토큰이 존재하지 않습니다.");
      }
    } else {
      log.debug("이미 사용자 인증 객체가 존재합니다 : {}",
          SecurityContextHolder.getContext().getAuthentication());
    }
    filterChain.doFilter(request, response);

  }

  private String getAccessToken(HttpServletRequest request) {
    String accessToken = request.getHeader("Authorization");

    if (accessToken != null && !accessToken.isBlank()) {
      try {
        accessToken = accessToken.replace("Bearer ", "");
        return URLDecoder.decode(accessToken, StandardCharsets.UTF_8);
      } catch (Exception e) {
        log.warn(e.getMessage(), e);
      }
    }
    return null;
  }

  private Jwt.Claims verify(String token) {
    return jwt.verify(token);
  }
}
