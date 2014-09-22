package ai.models.graph;

import java.awt.*;

import ai.models.Node;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public class ColorNode extends Node<ColorNode> implements VariableListener {

  private static final String TAG = ColorNode.class.getSimpleName();
  private double x;
  private double y;
  private final int index;
  private Color color = Color.white;
  private String desc = "";

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

  private Color toHsv(double i, double brightness) {
    float hue = (float) ((i / 100.) * 360.);
    float value = (float) (1f * brightness);
    float sat = 0.45f;

    return Color.getHSBColor(hue, sat, value);
  }

  public Color getColor() {
    return color;
  }

  @Override
  public void onValueChanged(int value, int size) {
    if (size == 1) {
      setColor(toHsv(value, 1));
    } else {
      setColor(toHsv(100, .7));
    }
  }

  public String getDescription() {
    return desc;
  }

  @Override
  public void onAssumptionMade(int value) {
  }

  @Override
  public void onDomainChanged(Domain domain) {
    desc = domain.toString();
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
