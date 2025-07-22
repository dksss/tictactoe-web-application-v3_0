package org.s21.tictactoe.domain.service.user;

import org.s21.tictactoe.security.dto.JwtRequest;
import org.s21.tictactoe.security.dto.JwtResponse;
import org.s21.tictactoe.security.model.JwtAuthentication;
import org.s21.tictactoe.web.dto.SignUpRequest;

import java.util.UUID;

public interface AuthorizationService {

  boolean registerUser(SignUpRequest signUpRequest);
  JwtResponse processAuth(JwtRequest jwtRequest);
  JwtResponse updateAccessToken(String jwtRefreshToken);
  JwtResponse updateRefreshToken(String jwtRefreshToken);
  JwtAuthentication getAuthentication();

}
