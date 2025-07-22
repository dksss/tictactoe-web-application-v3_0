package org.s21.tictactoe.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.service.user.AuthorizationService;
import org.s21.tictactoe.security.dto.JwtRequest;
import org.s21.tictactoe.security.dto.JwtResponse;
import org.s21.tictactoe.security.dto.RefreshJwtRequest;
import org.s21.tictactoe.web.dto.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthorizationService authorizationService;

  @PostMapping("/registration")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest request) {
    boolean isRegistered = authorizationService.registerUser(request);

    return isRegistered
            ? ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")
            : ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest loginRequest) {
    JwtResponse response = authorizationService.processAuth(loginRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/access")
  public ResponseEntity<JwtResponse> getAccessToken(@RequestBody RefreshJwtRequest request) {
    JwtResponse response = authorizationService.updateAccessToken(request.getRefreshToken());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<JwtResponse> refreshAccessToken(@RequestBody RefreshJwtRequest request) {
    JwtResponse response = authorizationService.updateRefreshToken(request.getRefreshToken());
    return ResponseEntity.ok(response);
  }
}
