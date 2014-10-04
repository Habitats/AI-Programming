package ai.models.grid;

import java.awt.*;
import java.util.ArrayList;

import ai.gui.ColorUtils;
import ai.models.Node;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public class ColorTile extends Node<Node> implements VariableListener {

  protected static final Color EMPTY = Color.WHITE;
  private static final String TAG = ColorTile.class.getSimpleName();
  public final int x;
  public final int y;
  protected int numberOfColors;
  private String text;
  private Color color = EMPTY;
  private Color outlineColor;

  public ColorTile(int x, int y, int numberOfColors) {
    this.x = x;
    this.y = y;
    this.numberOfColors = numberOfColors;
  }

  @Override
  public String toString() {
    return String.format("%s: %s, %s - Empty: %s", ColorTile.class.getSimpleName(), x, y, isEmpty());
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

  @Override
  public int compareTo(Node o) {
    return 0;
  }

  @Override
  public String getId() {
    return "x" + x + "y" + y;
  }

  @Override
  public boolean isEmpty() {
    return getColor() == EMPTY;
  }

  public void update(ColorTile colorTile) {
    setColor(colorTile.getColor());
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

  public void setColor(int colorIndex) {
    setColor(ColorUtils.toHsv(colorIndex, numberOfColors, 1));
  }

  public void setColor(int colorIndex, double brightness) {
    setColor(ColorUtils.toHsv(colorIndex, numberOfColors, brightness));
  }

  @Override
  public void onValueChanged(int value, int size) {
    if (size == 1) {
      setColor(ColorUtils.toHsv(value, numberOfColors, 1));
    } else {
      setColor(Color.white);
    }
  }

  @Override
  public void onAssumptionMade(int value) {
    setOutlineColor(ColorUtils.toHsv(value, numberOfColors, .7));
  }

  @Override
  public void onDomainChanged(Domain domain) {
  }

  public java.util.List<ColorTile> getManhattanNeightbors() {
    java.util.List<ColorTile> manhattanNeighbors = new ArrayList<>();
    for (int x = this.x - 1; x <= this.x + 1; x++) {
      for (int y = this.y - 1; y <= this.y + 1; y++) {
        // do not put self to its own children
        if (x == this.x && y == this.y) {
          continue;
        }
        // disallow diagonal neighbors
        if (this.x != x && this.y != y) {
          continue;
        }
        manhattanNeighbors.add(new ColorTile(x, y, numberOfColors));
      }
    }
    return manhattanNeighbors;
  }
}
