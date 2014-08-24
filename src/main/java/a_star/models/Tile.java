package a_star.models;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Tile {


  public enum State {
    OBSTICLE, OUTLINE, EMPTY, START, GOAL;
  }

  public final int x;
  public final int y;
  private State state;

  public Tile(int x, int y, State state) {
    this.x = x;
    this.y = y;
    this.state = state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  @Override
  public String toString() {
    return String.format("%s: %s, %s", Tile.class.getSimpleName(), x, y);
  }
}
