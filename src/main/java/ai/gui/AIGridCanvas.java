package ai.gui;

import java.awt.*;

import ai.models.grid.Board;
import ai.models.grid.Tile;
import puzzles.shortestpath.gui.ShortestPathGui;

/**
 * Created by Patrick on 08.09.2014.
 */
public class AIGridCanvas extends AICanvas {

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
          Tile tile = ((Board) getAdapter()).get(x, y);
          paintTile(g, tile);
        }
      }
    }
  }

  private void paintTile(Graphics g, Tile tile) {
    // set origin to be the left bottom corner
    int x = tile.x * tileWidth;
    int y = getHeight() - tileHeight - tile.y * tileHeight;

    drawTile(g, tile, x, y);

    if (drawLabels) {
      drawStringCenter(g, tile.getText(), x, y);
    }
    if (ShortestPathGui.DRAW_OUTLINES) {
      drawOutline(g, x, y);
    }
  }

  private void drawTile(Graphics g, Tile tile, int x, int y) {
    g.setColor(tile.getColor());
    g.fillRect(x, y, tileWidth, tileHeight);
  }

  @Override
  protected int getItemHeight() {
    return tileHeight;
  }

  @Override
  protected int getItemWidth() {
    return tileWidth;
  }

  protected void drawOutline(Graphics g, int x, int y) {
    g.setColor(Theme.getForeground());
    g.drawRect(x, y, tileWidth, tileHeight);
  }

  protected void updateMetrics() {
    if (getAdapter() == null) {
      return;
    }
    tileHeight = getHeight() / getAdapter().getHeight();
    tileWidth = getWidth() / getAdapter().getWidth();
  }
}
