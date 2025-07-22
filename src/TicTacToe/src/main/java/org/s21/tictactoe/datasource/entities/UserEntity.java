package org.s21.tictactoe.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private UUID id;

  @Column(unique = true, nullable = false)
  private String login;

  @Column(nullable = false)
  private String password;

  @ManyToMany
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

}
