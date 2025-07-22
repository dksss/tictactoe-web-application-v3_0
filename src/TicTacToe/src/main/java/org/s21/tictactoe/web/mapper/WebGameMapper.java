package org.s21.tictactoe.web.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.s21.tictactoe.domain.model.game.Board;
import org.s21.tictactoe.domain.model.game.Game;
import org.s21.tictactoe.web.dto.BoardWeb;
import org.s21.tictactoe.web.dto.GameWeb;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebGameMapper {

  public static GameWeb toWeb(Game game) {
    UUID id = game.getId();
    BoardWeb board = WebBoardMapper.toWeb(game.getBoard());
    Date creationDate = game.getCreationDate();

    return new GameWeb(id, creationDate, board);
  }

  public static Game toDomain(GameWeb gameWeb) {
    UUID id = gameWeb.getId();
    Board board = WebBoardMapper.toDomain(gameWeb.getBoard());
    Date creationDate = gameWeb.getCreationDate();

    return new Game(id, board, creationDate);
  }

}
