package ai.models.grid;

import java.awt.*;

/**
 * Created by Patrick on 03.10.2014.
 */
public class ColorTile extends Tile {

  private Color color;
  private Color outlineColor;

  public ColorTile(int x, int y) {
    super(x, y);
    setColor(Color.lightGray);
  }

  private Color toHsv(double i, double brightness) {
    float hue = (float) ((i / 100.) * 360.);
    float value = (float) (1f * brightness);
    float sat = 0.45f;

    return Color.getHSBColor(hue, sat, value);
  }

  @Override
  public boolean isEmpty() {
    return color == Color.white;
  }

  @Override
  public void update(Tile tile) {
    setColor(tile.getColor());
  }

  @Override
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

  public void setColor(int colorIndex, int numberOfColors) {
    double num = ((double) colorIndex) / numberOfColors * 100.;
    setColor(toHsv(num, 0.7));
  }
}
