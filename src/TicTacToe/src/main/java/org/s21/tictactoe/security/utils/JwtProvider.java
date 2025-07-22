package org.s21.tictactoe.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.s21.tictactoe.domain.exception.JwtValidationException;
import org.s21.tictactoe.domain.model.user.Role;
import org.s21.tictactoe.domain.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

@Component
public class JwtProvider {

  private final SecretKey jwtAccessSecret;
  private final Duration jwtAccessLifetime;

  private final SecretKey jwtRefreshSecret;
  private final Duration jwtRefreshLifetime;

  JwtProvider(@Value("${jwt.secret.access}") String base64AccessSecret,
              @Value("${jwt.secret.access-lifetime}") Duration accessTokenLifetime,
              @Value("${jwt.secret.refresh}") String base64RefreshSecret,
              @Value("${jwt.secret.refresh-lifetime}") Duration refreshTokenLifetime) {
    this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64AccessSecret));
    this.jwtAccessLifetime = accessTokenLifetime;
    this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64RefreshSecret));
    this.jwtRefreshLifetime = refreshTokenLifetime;
  }

  public String generateAccessToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId());
    claims.put("roles", user.getRoles().stream().map(Role::name).toList());

    return generateToken(claims, jwtAccessLifetime, jwtAccessSecret);
  }

  public String generateRefreshToken(User user) {
    Map<String, Object> claims = Map.of("userId", user.getId());
    return generateToken(claims, jwtRefreshLifetime, jwtRefreshSecret);
  }

  public boolean isAccessTokenValid(String token) throws JwtValidationException {
    return isValidToken(token, jwtAccessSecret);
  }

  public boolean isRefreshTokenValid(String token) throws JwtValidationException {
    return isValidToken(token, jwtRefreshSecret);
  }

  public Claims getAccessTokenClaims(String token) throws JwtValidationException {
    return getClaimsFromToken(token, jwtAccessSecret);
  }

  public Claims getRefreshTokenClaims(String token) throws JwtValidationException {
    return getClaimsFromToken(token, jwtRefreshSecret);
  }

  private String generateToken(Map<String, Object> claims, Duration lifetime, SecretKey key) {
    Date issuedDate = new Date();
    Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(issuedDate)
            .setExpiration(expiredDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  private boolean isValidToken(String token, SecretKey key) throws JwtValidationException {
    try {
      getClaimsFromToken(token, key);
      return true;
    } catch (JwtValidationException e) {
      return false;
    }
  }

  private Claims getClaimsFromToken(String token, SecretKey key) throws JwtValidationException {
    try {
      return Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (ExpiredJwtException e) {
      throw new JwtValidationException("Token expired");
    } catch (UnsupportedJwtException e) {
      throw new JwtValidationException("Token not supported");
    } catch (MalformedJwtException e) {
      throw new JwtValidationException("Token malformed");
    } catch (SecurityException e) {
      throw new JwtValidationException("Invalid signature");
    } catch (Exception e) {
      throw new JwtValidationException("Invalid token");
    }
  }

}
