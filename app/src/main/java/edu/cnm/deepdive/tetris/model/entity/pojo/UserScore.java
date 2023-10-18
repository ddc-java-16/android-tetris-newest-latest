package edu.cnm.deepdive.tetris.model.entity.pojo;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.tetris.model.entity.Score;
import edu.cnm.deepdive.tetris.model.entity.User;
import java.time.Instant;

@DatabaseView(
    viewName = "user_score",
    value = "SELECT s.player_id, u.display_name, s.value, s.rows_removed, s.created "
        + " FROM user AS u JOIN score AS s ON u.user_id = s.player_id "
        + "ORDER BY value DESC"
)
public class UserScore {


  @ColumnInfo(name = "player_id")
  private long playerId;
  @ColumnInfo(name = "display_name")
  private String displayName;


  private long value;

  @ColumnInfo(name = "rows_removed")
  private int rows_removed;
  private Instant created;


  public long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(long playerId) {
    this.playerId = playerId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public int getRows_removed() {
    return rows_removed;
  }

  public void setRows_removed(int rows_removed) {
    this.rows_removed = rows_removed;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }
}
