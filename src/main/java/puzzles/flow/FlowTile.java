package puzzles.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.models.grid.ColorTile;

/**
 * Created by Patrick on 14.10.2014.
 */
public class FlowTile extends ColorTile {

  public static final String SEP = "_";
  public static final String NEIGHBOR = "n" + SEP;
  public static final String OUTPUT = "o" + SEP;
  public static final String ID = "id" + SEP;
  public static final String INPUT = "i" + SEP;
  public static final int ABOVE = 0;
  public static final int RIGHT = 1;
  public static final int BELOW = 2;
  public static final int LEFT = 3;
  private ColorTile output;
  private ColorTile input;
  private String neighborId;
  private State state;

  private Map<Integer, FlowTile> manhattanNeighbors;

  public FlowTile(int x, int y, int numberOfColors) {
    super(x, y, numberOfColors);
    state = FlowTile.State.MID;
  }

  @Override
  public void update(ColorTile colorTile) {
    super.update(colorTile);
    state = ((FlowTile) colorTile).state;
  }

  public void setManhattanNeighbors(List<FlowTile> manhattanNeighbors) {
    this.manhattanNeighbors = new HashMap<>();
    for (FlowTile neighbor : manhattanNeighbors) {
      // if neighbor is below
      if (neighbor.x == x && neighbor.y < y) {
        this.manhattanNeighbors.put(FlowTile.BELOW, neighbor);
      }
      // if neighbor is above
      else if (neighbor.x == x && neighbor.y > y) {
        this.manhattanNeighbors.put(FlowTile.ABOVE, neighbor);
      }
      // if neighbor is left
      else if (neighbor.x < x && neighbor.y == y) {
        this.manhattanNeighbors.put(FlowTile.LEFT, neighbor);
      }
      // if neighbor is right
      else if (neighbor.x > x && neighbor.y == y) {
        this.manhattanNeighbors.put(FlowTile.RIGHT, neighbor);
      }
    }
  }

  public Map<Integer, FlowTile> getManhattanNeighbors() {
    return manhattanNeighbors;
  }

  public void setColorState(State state) {
    this.state = state;
  }

  public State getColorState() {
    return state;
  }

  public String getOutput() {
    return OUTPUT + "x" + x + "y" + y;
  }

  public String getInput() {
    return INPUT + "x" + x + "y" + y;
  }

  public void setOutput(ColorTile output) {
    this.output = output;
  }

  public void setInput(ColorTile input) {
    this.input = input;
  }

  public String getOutputNeighborId() {
    return NEIGHBOR + OUTPUT + getId();
  }

  public String getInputNeighborId() {
    return NEIGHBOR + INPUT + getId();
  }

  public enum State {
    START, END, MID
  }
}
