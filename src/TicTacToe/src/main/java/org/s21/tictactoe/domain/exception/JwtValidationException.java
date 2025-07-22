package org.s21.tictactoe.domain.exception;

public class JwtValidationException extends RuntimeException {
  public JwtValidationException(String message) {
    super(message);
  }
}
