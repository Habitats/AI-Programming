package ai.models.graph;

import java.awt.*;

import ai.gui.ColorUtils;
import ai.models.Node;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableListener;
import puzzles.graph_coloring.GraphColoringPuzzle;

/**
 * Created by Patrick on 08.09.2014.
 */
public class ColorNode extends Node<ColorNode> implements VariableListener<Integer> {

  private static final String TAG = ColorNode.class.getSimpleName();
  private static final Color EMPTY = Color.WHITE;
  private double x;
  private double y;
  private final int index;
  private Color color = EMPTY;
  private String desc = "";
  private Color outlineColor = Color.black;
  private final int numberOfColors = GraphColoringPuzzle.K;
  private int value;

  public ColorNode(double x, double y, int index) {
    this.x = x;
    this.y = y;
    this.index = index;
  }

  @Override
  public int compareTo(ColorNode o) {
    return 0;
  }

  public int getX() {
    return (int) (x * 100);
  }

  @Override
  public String getId() {
    return "n" + index;
  }

  @Override
  public boolean isEmpty() {
    return color == EMPTY;
  }

  public int getY() {
    return (int) (y * 100);
  }

  public void normalize(int minX, int minY) {
    x -= minX / 100.;
    y -= minY / 100.;
  }

  @Override
  public void onValueChanged(Integer value, int domainSize, Variable<Integer> variable) {
    this.value = value;
    if (domainSize == 1) {
      setColor(ColorUtils.toHsv(value, numberOfColors, 1));
    } else {
      setColor(Color.white);
    }
  }

  public String getDescription() {
    return desc;
  }

  @Override
  public void onAssumptionMade(Integer value, Variable<Integer> variable) {
    setOutlineColor(ColorUtils.toHsv(value, numberOfColors, .7));
  }

  @Override
  public void onDomainChanged(Domain<Integer> domain, Variable<Integer> tVariable) {
    desc = domain.toString();
  }

  @Override
  public Integer getInitialValue() {
    return value;
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
}
