package ai.models.graph;

import java.awt.*;

import ai.models.Node;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public class ColorNode extends Node<ColorNode> implements VariableListener {

  private double x;
  private double y;
  private final int index;
  private Color color;
  private Variable variable;

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

  public String getId() {
    return "n" + index;
  }

  public int getY() {
    return (int) (y * 100);
  }

  public void normalize(int minX, int minY) {
    x -= minX / 100.;
    y -= minY / 100.;
  }

  private Color toHsv(double i) {
    float hue = (float) ((i / 100.) * 360.);
    float value = 0.93f;
    float sat = 0.95f;

    return Color.getHSBColor(hue, sat, value);
  }

  public Color getColor() {
    return color;
  }

  @Override
  public void onValueChanged(int value) {
    setColor(toHsv(value));
  }

  @Override
  public void onAssumptionMade(int value) {

  }

  public void setColor(Color color) {
    this.color = color;
  }
}
