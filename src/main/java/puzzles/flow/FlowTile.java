package puzzles.flow;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gui.AICanvas;
import ai.gui.ColorUtils;
import ai.models.grid.ColorTile;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;

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

  private State state;
  private String domainInputText = "";
  private String domainOuputText = "";
  private String domainColorText = "";

  private Map<Integer, FlowTile> manhattanNeighbors;
  private AICanvas.Direction direction;

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

  public void setDirection(AICanvas.Direction direction) {
    this.direction = direction;
  }

  public AICanvas.Direction getDirection() {
    return direction;
  }

  public enum State {
    START, END, MID
  }

  @Override
  public void onDomainChanged(Domain<Integer> domain, Variable<Integer> variable) {
    boolean color = variable.getId().startsWith(FlowTile.ID);
    boolean output = variable.getId().startsWith(FlowTile.OUTPUT);
    boolean input = variable.getId().startsWith(FlowTile.INPUT);
    if (color) {
      domainColorText = domain.getId();
    } else if (output) {
      domainOuputText = domain.getId();
    } else if (input) {
      domainInputText = domain.getId();
    }
  }

  @Override
  public String toString() {
    return "C: " + domainColorText //
           + ((domainInputText.length() > 0) ? " - I: " + domainInputText : "") //
           + ((domainOuputText.length() > 0) ? " - O: " + domainOuputText : "");
  }

  @Override
  public void onValueChanged(Integer value, int domainSize, Variable<Integer> variable) {
    drawTile(value, domainSize, variable);
  }

  private void drawTile(Integer value, int domainSize, Variable<Integer> variable) {
    boolean color = variable.getId().startsWith(FlowTile.ID);
    boolean output = variable.getId().startsWith(FlowTile.OUTPUT);
    boolean input = variable.getId().startsWith(FlowTile.INPUT);

    if (color) {
      if (domainSize == 1) {
        setColor(ColorUtils.toHsv(value, getNumberOfColors(), 1));
      } else {
        setColor(Color.white);
      }
    } else if (output) {
      if (domainSize == 1) {
        setDirection(AICanvas.Direction.getDirection(value));
      } else {
        setDirection(null);
      }
    }
  }
}
