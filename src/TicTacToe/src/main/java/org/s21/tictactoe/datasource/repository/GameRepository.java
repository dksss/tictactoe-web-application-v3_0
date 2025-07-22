package org.s21.tictactoe.datasource.repository;

import org.s21.tictactoe.datasource.entities.GameEntity;
import org.s21.tictactoe.domain.model.game.LeaderboardRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, UUID> {

  @Query("SELECT DISTINCT g "
          + "FROM GameEntity g "
          + "WHERE g.xMarkPlayerId = :playerId OR g.oMarkPlayerId = :playerId")
  List<GameEntity> findAvailableGames(@Param("playerId") UUID playerId);

  @Query("SELECT DISTINCT g " +
          "FROM GameEntity g " +
          "WHERE (g.xMarkPlayerId = :playerId OR g.oMarkPlayerId = :playerId)" +
          "AND (g.winnerId IS NOT NULL OR g.isDraw = true)")
  List<GameEntity> findAllFinishedGames(@Param("playerId") UUID playerId);

  @Query(value = """
    WITH
    wins_per_player AS (
    	SELECT
    		winner AS player_id,
    		count(*) AS wins
    	FROM games
    	WHERE winner IS NOT NULL AND game_state LIKE 'GAME_OVER'
    	GROUP BY winner
    ),
    all_games_per_player AS (
    	SELECT u.login, u.id AS player_id, COUNT(*) AS all_games
    	FROM users u
    		JOIN (
        		SELECT x_mark AS player_id FROM games WHERE game_state LIKE 'GAME_OVER' AND x_mark IS NOT NULL
        		UNION ALL
        		SELECT o_mark AS player_id FROM games WHERE game_state LIKE 'GAME_OVER' AND o_mark IS NOT NULL
      		) g ON u.id = g.player_id
    	GROUP BY u.login, u.id
    )
    SELECT
    	a.login,
    	a.player_id,
    	(COALESCE(w.wins, 0) * 1.0 / a.all_games)::float AS win_ratio
    FROM
    	all_games_per_player a LEFT JOIN wins_per_player w ON a.player_id = w.player_id
    ORDER BY win_ratio DESC
    LIMIT :limit;
    """, nativeQuery = true)
  List<LeaderboardRecord> findTopPlayers(@Param("limit") int limit);

}
