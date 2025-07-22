package org.s21.tictactoe.web.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.s21.tictactoe.domain.model.user.Role;
import org.s21.tictactoe.domain.model.user.User;
import org.s21.tictactoe.web.dto.UserWeb;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebUserMapper {

  public static UserWeb toWeb(User user) {
    UUID id = user.getId();
    String login = user.getLogin();
    Set<String> roles = user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet());

    return new UserWeb(id, login, roles);
  }

}
