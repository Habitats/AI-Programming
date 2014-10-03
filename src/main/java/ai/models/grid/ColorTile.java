package ai.models.grid;

import java.awt.*;

import ai.gui.ColorUtils;

/**
 * Created by Patrick on 03.10.2014.
 */
public class ColorTile extends Tile {

  private static final Color EMPTY = Color.WHITE;
  private static final String TAG = ColorTile.class.getSimpleName();
  private Color color;
  private Color outlineColor;

  public ColorTile(int x, int y) {
    super(x, y);
    setColor(EMPTY);
  }

  @Override
  public boolean isEmpty() {
    return getColor() == EMPTY;
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
    setColor(ColorUtils.toHsv(colorIndex, numberOfColors, 1));
  }
}
