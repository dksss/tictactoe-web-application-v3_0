package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.model.game.Game;
import org.s21.tictactoe.domain.model.game.Position;

public interface AiService {

  Position getBestMove(Game game);

}
