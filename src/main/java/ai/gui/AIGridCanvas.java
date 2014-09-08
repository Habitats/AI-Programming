package ai.gui;

import java.awt.*;

import ai.models.Board;
import ai.models.Tile;
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

    switch (tile.getState()) {
      case OBSTICLE:
        g.setColor(Theme.getButtonBackground());
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case OUTLINE:
        g.setColor(Color.RED);
        g.drawRect(x, y, tileWidth, tileHeight);
        break;
      case EMPTY:
        g.setColor(Theme.getBackground());
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case START:
        g.setColor(Color.BLUE);
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case GOAL:
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case PATH:
        g.setColor(Color.RED);
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case CHILDREN:
        g.setColor(Theme.getButtonHover());
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case CURRENT:
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, tileWidth, tileHeight);
    }
    if (drawLabels) {
      drawStringCenter(g, tile.getText(), x, y);
    }
    if (ShortestPathGui.DRAW_OUTLINES) {
      drawOutline(g, x, y);
    }
  }

  @Override
  protected int itemHeigth() {
    return tileHeight;
  }

  @Override
  protected int itemWidth() {
    return tileWidth;
  }

  @Override
  protected void draw(Graphics g) {
    paintsBoard(g);
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
