package puzzles.shortestpath;

import java.awt.*;

import ai.gui.AICanvas;
import ai.gui.Theme;
import ai.models.grid.ColorTile;

/**
 * Created by Patrick on 03.10.2014.
 */
public class AStarColorTile extends ColorTile {

  private State state;
  private State previousState;
  private AICanvas.Direction direction;

  public AStarColorTile(int x, int y, State state) {
    super(x, y, State.values().length);
    setState(state);
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
  public boolean isEmpty() {
    return state != State.OBSTICLE;
  }

  @Override
  public void update(ColorTile colorTile) {
    setState(((AStarColorTile) colorTile).getState());
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

  @Override
  public String getId() {
    return null;
  }

  public void setDirection(AICanvas.Direction direction) {
    this.direction = direction;
  }

  public AICanvas.Direction getDirection() {
    return direction;
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
