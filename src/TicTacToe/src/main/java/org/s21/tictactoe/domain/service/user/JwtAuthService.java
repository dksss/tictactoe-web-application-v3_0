package org.s21.tictactoe.domain.service.user;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.exception.AccessDeniedException;
import org.s21.tictactoe.domain.exception.InvalidUserException;
import org.s21.tictactoe.domain.exception.JwtValidationException;
import org.s21.tictactoe.domain.model.user.Role;
import org.s21.tictactoe.domain.model.user.User;
import org.s21.tictactoe.security.dto.JwtRequest;
import org.s21.tictactoe.security.dto.JwtResponse;
import org.s21.tictactoe.security.model.JwtAuthentication;
import org.s21.tictactoe.security.utils.JwtProvider;
import org.s21.tictactoe.web.dto.SignUpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthorizationService {

  private final UserService userService;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;

  @Override
  public boolean registerUser(SignUpRequest signUpRequest) {
    if (userService.isUserExists(signUpRequest.getLogin())) {
      return false;
    }

    UUID userId = UUID.randomUUID();
    String userLogin = signUpRequest.getLogin();
    String userHashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

    User user = new User(userId, userLogin, userHashedPassword, Set.of(Role.ROLE_USER));
    userService.save(user);

    return true;
  }

  @Override
  public JwtResponse processAuth(JwtRequest jwtRequest) throws JwtValidationException, InvalidUserException {
    User user = userService.getUserByLogin(jwtRequest.getLogin());

    String rawPassword = jwtRequest.getPassword();
    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
      throw new AccessDeniedException("Wrong password");
    }

    String jwtAccessToken = jwtProvider.generateAccessToken(user);
    String jwtRefreshToken = jwtProvider.generateRefreshToken(user);

    return new JwtResponse(jwtAccessToken, jwtRefreshToken);
  }

  @Override
  public JwtResponse updateAccessToken(String jwtRefreshToken) throws JwtValidationException, InvalidUserException {
    User existUser = getUserByRefreshToken(jwtRefreshToken);
    String newJwtAccessToken = jwtProvider.generateAccessToken(existUser);
    return new JwtResponse(newJwtAccessToken, jwtRefreshToken);
  }

  @Override
  public JwtResponse updateRefreshToken(String jwtRefreshToken) throws JwtValidationException, InvalidUserException {
    User existUser = getUserByRefreshToken(jwtRefreshToken);
    String newJwtAccessToken = jwtProvider.generateAccessToken(existUser);
    String newJwtRefreshToken = jwtProvider.generateRefreshToken(existUser);
    return new JwtResponse(newJwtAccessToken, newJwtRefreshToken);
  }

  @Override
  public JwtAuthentication getAuthentication() {
    return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

  private User getUserByRefreshToken(String jwtRefreshToken) throws JwtValidationException, InvalidUserException {
    if (!jwtProvider.isRefreshTokenValid(jwtRefreshToken)) {
      throw new JwtValidationException("Invalid refresh token");
    }

    Claims claims = jwtProvider.getRefreshTokenClaims(jwtRefreshToken);
    String userIdStr = claims.get("userId", String.class);

    return userService.getUserById(UUID.fromString(userIdStr));
  }

}
