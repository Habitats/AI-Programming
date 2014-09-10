package ai.models;

/**
 * Created by Patrick on 08.09.2014.
 */
public class Drawable {

  public final int x;
  public final int y;
  protected State state;
  private State previousState;
  private String text;

  public Drawable(int x, int y, State state) {
    setState(state);
    this.x = x;
    this.y = y;
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
}
