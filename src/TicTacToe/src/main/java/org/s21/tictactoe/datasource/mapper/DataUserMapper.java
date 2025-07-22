package org.s21.tictactoe.datasource.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.s21.tictactoe.datasource.entities.RoleEntity;
import org.s21.tictactoe.datasource.entities.UserEntity;
import org.s21.tictactoe.domain.model.user.Role;
import org.s21.tictactoe.domain.model.user.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataUserMapper {

  public static UserEntity toDatasource(User user) {
    UUID id = user.getId();
    String login = user.getLogin();
    String password = user.getPassword();
    Set<RoleEntity> roles = user.getRoles().stream()
            .map(DataRoleMapper::toDatasource)
            .collect(Collectors.toSet());

    return new UserEntity(id, login, password, roles);
  }

  public static User toDomain(UserEntity userEntity) {
    UUID id = userEntity.getId();
    String login = userEntity.getLogin();
    String password = userEntity.getPassword();
    Set<Role> roles = userEntity.getRoles().stream()
            .map(DataRoleMapper::toDomain)
            .collect(Collectors.toSet());

    return new User(id, login, password, roles);
  }

}
