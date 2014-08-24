package a_star.gui;

import java.awt.*;

import javax.swing.*;

import a_star.interfaces.BoardListener;
import a_star.models.Board;
import a_star.models.Tile;
import aiprog.Log;

/**
 * Created by Patrick on 24.08.2014.
 */
public class BoardCanvas extends JPanel implements BoardListener {

  private static final String TAG = BoardCanvas.class.getSimpleName();
  private Board board;
  private int tileHeight;
  private int tileWidth;
  private Board adapter;

  public BoardCanvas() {
    setBackground(Color.BLACK);
    tileHeight = 20;
    tileWidth = 20;
    Log.v(TAG, getHeight() + "");
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    updateGridMetrics();
    paintsBoard(g);
  }

  private void paintsBoard(Graphics g) {
    for (int x = 0; x < board.width; x++) {
      for (int y = 0; y < board.height; y++) {
        Tile tile = board.get(x, y);
        paintTile(g, tile);
      }
    }
  }

  private void paintTile(Graphics g, Tile tile) {
    // set origin to be the left bottom corner
    int x = tile.x * tileWidth;
    int y = getHeight() - tileHeight - tile.y * tileHeight;

    switch (tile.getState()) {
      case OBSTICLE:
        g.setColor(Color.RED);
        g.fillRect(x, y, tileWidth, tileHeight);
        break;
      case OUTLINE:
        g.setColor(Color.RED);
        g.drawRect(x, y, tileWidth, tileHeight);
        break;
      case EMPTY:
        g.setColor(Color.BLACK);
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
    }
    g.setColor(Color.WHITE);
    g.drawRect(x, y, tileWidth, tileHeight);
  }

  private void updateGridMetrics() {
    if (board == null) {
      return;
    }
    tileHeight = getHeight() / board.height;
    tileWidth = getWidth() / board.width;
  }

  @Override
  public void notifyDataChanged() {
    repaint();
  }

  public void setAdapter(Board board) {
    this.board = board;
    board.setListener(this);
  }
}
