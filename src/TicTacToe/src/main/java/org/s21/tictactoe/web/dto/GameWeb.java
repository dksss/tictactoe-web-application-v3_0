package org.s21.tictactoe.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameWeb {

  private UUID id;
  private Date creationDate;
  private BoardWeb board;

}
