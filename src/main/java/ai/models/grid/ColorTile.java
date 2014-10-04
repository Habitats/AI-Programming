package ai.models.grid;

import java.awt.*;

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

  // final fields
  public final int x;
  public final int y;
  private final int numberOfColors;

  // mutable fields
  private Color color = EMPTY;
  private Color outlineColor;
  private String domainText;
  private Integer initialValue = null;

  public ColorTile(int x, int y, int numberOfColors) {
    this.x = x;
    this.y = y;
    this.numberOfColors = numberOfColors;
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
    return "x" + x + "y" + y;
  }

  @Override
  public boolean isEmpty() {
    return getColor() == EMPTY;
  }

  @Override
  public int getInitialValue() {
    return this.initialValue;
  }

  // update all the mutable fields
  public void update(ColorTile colorTile) {
    color = colorTile.color;
    initialValue = colorTile.initialValue;
    outlineColor = colorTile.outlineColor;
    domainText = colorTile.domainText;
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
    domainText = domain.toString();
  }


}
