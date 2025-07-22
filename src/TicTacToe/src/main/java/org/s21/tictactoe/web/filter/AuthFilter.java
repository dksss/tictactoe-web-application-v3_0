package org.s21.tictactoe.web.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.exception.JwtValidationException;
import org.s21.tictactoe.security.model.JwtAuthentication;
import org.s21.tictactoe.security.utils.JwtProvider;
import org.s21.tictactoe.security.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilter extends GenericFilterBean {

  private final JwtProvider jwtProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    String accessToken = getTokenFromRequest((HttpServletRequest) request);
    try {
      if (jwtProvider.isAccessTokenValid(accessToken)) {
        Claims claims = jwtProvider.getAccessTokenClaims(accessToken);
        JwtAuthentication auth = JwtUtil.generateAuth(claims);
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }

      chain.doFilter(request, response);
    } catch (JwtValidationException ex) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authData = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authData != null && authData.startsWith("Bearer ")) {
      return authData.substring(7);
    }
    return null;
  }

}
