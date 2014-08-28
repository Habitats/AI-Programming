package ai.models;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Tile {


  private State previousState;

  public enum State {
    OBSTICLE("x"), OUTLINE("."), EMPTY("_"), START("@"), GOAL("$"), PATH("o"), CHILDREN("c"), CURRENT("*");
    private final String state;

    State(String state) {
      this.state = state;
    }


    @Override
    public String toString() {
      return state;
    }
  }

  public final int x;
  public final int y;
  private State state;
  private String text;

  public Tile(int x, int y, State state) {
    this.x = x;
    this.y = y;
    this.state = state;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    if (text == null) {
      return toString();
    }
    return text;
  }

  public State getPreviousState() {
    return previousState;
  }

  public void setState(State state) {
    if (this.state != state) {
      previousState = this.state;
    }
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
