package puzzles.shortestpath;

import java.awt.*;

import ai.gui.Theme;
import ai.models.grid.Tile;

/**
 * Created by Patrick on 03.10.2014.
 */
public class AStarTile extends Tile {

  protected State state;
  private State previousState;

  public AStarTile(int x, int y, State state) {
    super(x, y);
    setState(state);
  }

  public AStarTile(int x, int y) {
    super(x, y);
    setState(State.EMPTY);
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

  public boolean isEmpty() {
    return state != State.OBSTICLE;
  }

  @Override
  public void update(Tile tile) {
    setState(((AStarTile) tile).getState());
  }

  @Override
  public Color getColor() {
    switch (getState()) {
      case OBSTICLE:
        return Theme.getButtonBackground();
      case OUTLINE:
        return Color.RED;
      case EMPTY:
        return Theme.getBackground();
      case START:
        return Color.BLUE;
      case GOAL:
        return Color.ORANGE;
      case PATH:
        return Color.RED;
      case CHILDREN:
        return Theme.getButtonHover();
      case CURRENT:
        return Color.MAGENTA;
      default:
        return Theme.getButtonBackground();
    }
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
