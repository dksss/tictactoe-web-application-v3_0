package org.s21.tictactoe.datasource.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.s21.tictactoe.datasource.entities.RoleEntity;
import org.s21.tictactoe.domain.model.user.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataRoleMapper {

  public static RoleEntity toDatasource(Role role) {
    int id = role.getId();
    String roleName = role.getName();
    return new RoleEntity(id, roleName);
  }

  public static Role toDomain(RoleEntity roleEntity) {
    return Enum.valueOf(Role.class, roleEntity.getName());
  }
}
