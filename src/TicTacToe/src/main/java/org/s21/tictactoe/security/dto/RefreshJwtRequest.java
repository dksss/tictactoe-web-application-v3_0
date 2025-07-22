package org.s21.tictactoe.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {

  private String refreshToken;

}
