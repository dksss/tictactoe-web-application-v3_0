package org.s21.tictactoe.domain.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

  ROLE_USER(1, "ROLE_USER");

  private final int id;
  private final String name;

  @Override
  public String getAuthority() {
    return name;
  }

}
