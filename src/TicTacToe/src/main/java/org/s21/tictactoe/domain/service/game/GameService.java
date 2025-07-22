package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.exception.InvalidGameException;
import org.s21.tictactoe.domain.model.game.Game;
import org.s21.tictactoe.domain.model.game.GameState;
import org.s21.tictactoe.domain.model.game.LeaderboardRecord;

import java.util.List;
import java.util.UUID;

public interface GameService {

  Game startGame(UUID playerId, GameState state);

  List<Game> getAvailableGames(UUID playerId);

  List<Game> getFinishedGames(UUID playerId);

  List<LeaderboardRecord> getTopPlayers(int limit);

  Game joinGame(UUID gameId, UUID playerId) throws InvalidGameException;

  Game makeAiMove(Game game);

  Game makeMove(Game game, UUID playerId) throws InvalidGameException;

  boolean isValid(Game game) throws InvalidGameException;

  boolean isGameOver(Game game);

  Game getGameById(UUID id) throws InvalidGameException;

}
