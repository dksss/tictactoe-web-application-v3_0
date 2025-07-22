package org.s21.tictactoe.domain.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Game {

  private UUID id;
  private Board board;

  private GameState state;
  private UUID currentPlayerId;
  private boolean isDraw;
  private UUID winnerId;

  private UUID xMarkPlayerId;
  private UUID oMarkPlayerId;

  private Date creationDate;

  public Game(UUID playerId, GameState state) {
    this.id = UUID.randomUUID();
    this.board = new Board();

    this.state = state;
    this.currentPlayerId = playerId;
    this.isDraw = false;
    this.winnerId = null;

    this.xMarkPlayerId = playerId;
    this.oMarkPlayerId = null;

    this.creationDate = new Date();
  }

  public Game(UUID id, Board board, Date creationDate) {
    this.id = id;
    this.board = board;
    this.creationDate = creationDate;
  }

  public void makeMove(Position pos, int value) {
    board.setValue(pos, value);
  }

}
