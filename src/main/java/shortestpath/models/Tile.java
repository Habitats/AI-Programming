package shortestpath.models;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Tile {



  public enum State {
    OBSTICLE("x"), OUTLINE("."), EMPTY("_"), START("@"), GOAL("$"), PATH("o"), CHILDREN("c");
    private final String x;

    State(String x) {
      this.x = x;
    }

    @Override
    public String toString() {
      return x;
    }
  }

  public final int x;
  public final int y;
  private State state;
  private String text = toString();

  public Tile(int x, int y, State state) {
    this.x = x;
    this.y = y;
    this.state = state;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
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
