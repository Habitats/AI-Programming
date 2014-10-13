package ai.models.grid;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gui.ColorUtils;
import ai.models.Node;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public class ColorTile extends Node<Node> implements VariableListener<Integer> {

  private State state;

  public void setColorState(State state) {
    this.state = state;
  }

  public State getColorState() {
    return state;
  }

  public enum State {
    START, END, MID
  }

  protected static final Color EMPTY = Color.WHITE;
  private static final String TAG = ColorTile.class.getSimpleName();

  // final fields
  public final int x;
  public final int y;
  private final int numberOfColors;

  // mutable fields
  private Color color = EMPTY;
  private Color outlineColor;
  private String domainText;
  private Integer initialValue = null;
  private ColorTile output;
  private ColorTile input;
  private Map<Integer, ColorTile> manhattanNeighbors;
  private String neighborId;
  public static State STATE;

  public static final java.lang.String SEP = "_";
  public static final String INPUT = "i" + SEP;
  public static final String ID = "id" + SEP;
  public static final String OUTPUT = "o" + SEP;
  public static final String NEIGHBOR = "n" + SEP;

  public static final int ABOVE = 0;
  public static final int RIGHT = 1;
  public static final int BELOW = 2;
  public static final int LEFT = 3;

  public ColorTile(int x, int y, int numberOfColors) {
    this.x = x;
    this.y = y;
    this.numberOfColors = numberOfColors;
    state = State.MID;
  }

  public void setInitialValue(int initialValue) {
    if (this.initialValue == null) {
      this.initialValue = initialValue;
    } else {
      throw new IllegalArgumentException("InitialValue already set!");
    }
  }

  @Override
  public String toString() {
    return String.format("%s - %s", getId(), domainText);
  }

  public void setDomainText(String domainText) {
    this.domainText = domainText;
  }

  public String getDomainText() {
    if (domainText == null) {
      return toString();
    }
    return domainText;
  }

  @Override
  public int compareTo(Node o) {
    return 0;
  }

  @Override
  public String getId() {
    return ID + "x" + x + "y" + y;
  }

  @Override
  public boolean isEmpty() {
    return getColor() == EMPTY;
  }

  @Override
  public Integer getInitialValue() {
    return this.initialValue;
  }

  // update all the mutable fields
  public void update(ColorTile colorTile) {
    color = colorTile.color;
    initialValue = colorTile.initialValue;
    outlineColor = colorTile.outlineColor;
    domainText = colorTile.domainText;
    state = colorTile.state;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setOutlineColor(Color outlineColor) {
    this.outlineColor = outlineColor;
  }

  public Color getOutlineColor() {
    return outlineColor;
  }

  public void setColor(int value) {
    setColor(ColorUtils.toHsv(value, numberOfColors, 1));
  }

  public void setColor(int value, double brightness) {
    setColor(ColorUtils.toHsv(value, numberOfColors, brightness));
  }

  @Override
  public void onValueChanged(Integer value, int size) {
    if (size == 1) {
      setColor(ColorUtils.toHsv(value, numberOfColors, 1));
    } else {
      setColor(Color.white);
    }
  }

  @Override
  public void onAssumptionMade(Integer value) {
    setOutlineColor(ColorUtils.toHsv(value, numberOfColors, .7));
  }

  @Override
  public void onDomainChanged(Domain domain) {
    domainText = domain.toString();
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

  public void setManhattanNeighbors(List<ColorTile> manhattanNeighbors) {
    this.manhattanNeighbors = new HashMap<>();
    for (ColorTile neighbor : manhattanNeighbors) {
      // if neighbor is below
      if (neighbor.x == x && neighbor.y < y) {
        this.manhattanNeighbors.put(BELOW, neighbor);
      }
      // if neighbor is above
      else if (neighbor.x == x && neighbor.y > y) {
        this.manhattanNeighbors.put(ABOVE, neighbor);
      }
      // if neighbor is left
      else if (neighbor.x < x && neighbor.y == y) {
        this.manhattanNeighbors.put(LEFT, neighbor);
      }
      // if neighbor is right
      else if (neighbor.x > x && neighbor.y == y) {
        this.manhattanNeighbors.put(RIGHT, neighbor);
      }
    }
  }

  public Map<Integer, ColorTile> getManhattanNeighbors() {
    return manhattanNeighbors;
  }

  public String getOutputNeighborId() {
    return NEIGHBOR + OUTPUT + getId();
  }

  public String getInputNeighborId() {
    return NEIGHBOR + INPUT + getId();
  }
}
