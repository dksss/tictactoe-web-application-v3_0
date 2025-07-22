package org.s21.tictactoe.web.controller;

import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.service.user.UserService;
import org.s21.tictactoe.security.model.JwtAuthentication;
import org.s21.tictactoe.web.mapper.WebUserMapper;
import org.s21.tictactoe.web.dto.UserWeb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<UserWeb> getUserInfoById(@PathVariable UUID userId) {
    UserWeb user = WebUserMapper.toWeb(userService.getUserById(userId));
    return ResponseEntity.ok(user);
  }

  @GetMapping("/info")
  public ResponseEntity<?> getUserInfoByToken(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    UUID userId = ((JwtAuthentication) authentication).getUserId();
    UserWeb user = WebUserMapper.toWeb(userService.getUserById(userId));
    return ResponseEntity.ok(user);
  }

}
