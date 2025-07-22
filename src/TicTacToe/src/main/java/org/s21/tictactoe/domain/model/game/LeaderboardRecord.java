package org.s21.tictactoe.domain.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LeaderboardRecord {

  private String playerLogin;
  private UUID playerId;
  private double winRatio;

}
