package org.s21.tictactoe.domain.service.user;

import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.datasource.mapper.DataUserMapper;
import org.s21.tictactoe.datasource.repository.UserRepository;
import org.s21.tictactoe.domain.exception.InvalidUserException;
import org.s21.tictactoe.domain.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Service
@SessionScope
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getUserByLogin(String login) throws InvalidUserException {
    return userRepository.findByLogin(login)
            .map(DataUserMapper::toDomain)
            .orElseThrow(() -> new InvalidUserException("User with this login not registered"));
  }

  @Override
  public User getUserById(UUID userId) throws InvalidUserException {
    return userRepository.findById(userId)
            .map(DataUserMapper::toDomain)
            .orElseThrow(() -> new InvalidUserException("User with this id not registered"));
  }

  @Override
  public void save(User user) {
    userRepository.save(DataUserMapper.toDatasource(user));
  }

  @Override
  public boolean isUserExists(String login) {
    return userRepository.existsByLogin(login);
  }

}
