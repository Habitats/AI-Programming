package ai.gui;

import java.awt.*;

import ai.models.grid.Board;
import ai.models.grid.ColorTile;
import puzzles.shortestpath.gui.ShortestPathGui;

/**
 * Created by Patrick on 08.09.2014.
 */
public class AIGridCanvas<T extends ColorTile> extends AICanvas {

  private int tileHeight = 20;
  private int tileWidth = 20;

  public AIGridCanvas() {
    setBackground(Color.BLACK);
  }

  @Override
  protected void draw(Graphics2D g) {
    paintsBoard(g);
  }

  private void paintsBoard(Graphics g) {
    if (getAdapter() != null) {
      for (int x = 0; x < getAdapter().getWidth(); x++) {
        for (int y = 0; y < getAdapter().getHeight(); y++) {
          T colorTile = (T) ((Board) getAdapter()).get(x, y);
          paintTile(g, colorTile);
        }
      }
    }
  }

  protected void paintTile(Graphics g, T colorTile) {
    // put origin to be the left bottom corner
    int x = colorTile.x * getTileWidth();
    int y = getHeight() - getTileHeight() - colorTile.y * getTileHeight();

    drawTile(g, colorTile, x, y);


    if (drawLabels) {
      drawStringCenter(g, colorTile.getDescription(), x, y);
    }

    if (ShortestPathGui.DRAW_OUTLINES) {
      drawOutline(g, x, y);
    }
  }

  private void drawArrow(Graphics g, int x, int y, Direction direction) {
    Point start = new Point();
    Point end = new Point();
    int arrowLength = 40;
    switch (direction) {
      case UP:
        start.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) + (arrowLength / 2));
        end.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) - (arrowLength / 2));
        break;
      case RIGHT:
        start.setLocation(x + (getTileWidth() / 2) - (arrowLength / 2), y + (getTileHeight() / 2));
        end.setLocation(x + (getTileWidth() / 2) + (arrowLength / 2), y + (getTileHeight() / 2));
        break;
      case DOWN:
        start.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) - (arrowLength / 2));
        end.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) + (arrowLength / 2));
        break;
      case LEFT:
        start.setLocation(x + (getTileWidth() / 2) + (arrowLength / 2), y + (getTileHeight() / 2));
        end.setLocation(x + (getTileWidth() / 2) - (arrowLength / 2), y + (getTileHeight() / 2));
        break;
    }
    createArrowShape((Graphics2D) g, start, end);
  }

  protected void drawTile(Graphics g, T colorTile, int x, int y) {
    g.setColor(colorTile.getColor());
    g.fillRect(x, y, getTileWidth(), getTileHeight());
  }

  @Override
  protected int getItemHeight() {
    return getTileHeight();
  }

  @Override
  protected int getItemWidth() {
    return getTileWidth();
  }

  @Override
  protected void drawOutline(Graphics g, int x, int y) {
    g.setColor(Theme.getForeground());
    g.drawRect(x, y, getTileWidth(), getTileHeight());
  }

  @Override
  protected void updateMetrics() {
    if (getAdapter() == null) {
      return;
    }
    tileHeight = getHeight() / getAdapter().getHeight();
    tileWidth = getWidth() / getAdapter().getWidth();
  }

  public int getTileHeight() {
    return tileHeight;
  }

  public int getTileWidth() {
    return tileWidth;
  }
}
