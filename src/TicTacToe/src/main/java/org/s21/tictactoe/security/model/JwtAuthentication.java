package org.s21.tictactoe.security.model;

import lombok.Getter;
import lombok.Setter;
import org.s21.tictactoe.domain.model.user.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class JwtAuthentication implements Authentication {

  private boolean isAuthenticated;
  private UUID userId;
  private Set<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return userId;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return userId.toString();
  }
}
