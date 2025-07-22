package org.s21.tictactoe.security.utils;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.s21.tictactoe.domain.model.user.Role;
import org.s21.tictactoe.security.model.JwtAuthentication;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {

  public static JwtAuthentication generateAuth(Claims claims) {
    JwtAuthentication authInfo = new JwtAuthentication();

    String userIdStr = claims.get("userId", String.class);
    UUID userId = UUID.fromString(userIdStr);
    authInfo.setUserId(userId);

    authInfo.setRoles(getRolesFromClaims(claims));

    return authInfo;
  }

  private static Set<Role> getRolesFromClaims(Claims claims) {
    List<?> rolesRaw = claims.get("roles", List.class);
    if (rolesRaw == null) {
      return Set.of();
    }

    return rolesRaw.stream()
            .map(Object::toString)
            .map(Role::valueOf)
            .collect(Collectors.toSet());
  }

}
