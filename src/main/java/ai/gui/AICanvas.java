package ai.gui;

import java.awt.*;

import javax.swing.*;

import ai.Log;
import ai.models.Board;
import ai.models.Tile;
import puzzles.shortestpath.interfaces.BoardListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AICanvas extends JPanel implements BoardListener {

  private static final String TAG = AICanvas.class.getSimpleName();
  private Board board;
  private int tileHeight;
  private int tileWidth;
  private Board adapter;

  public AICanvas() {
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
    if (board != null) {
      for (int x = 0; x < board.width; x++) {
        for (int y = 0; y < board.height; y++) {
          Tile tile = board.get(x, y);
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
    drawStringCenter(g, tile.getText(), x, y);
    drawOutline(g, x, y);
  }

  private void drawOutline(Graphics g, int x, int y) {
    g.setColor(Theme.getForeground());
    g.drawRect(x, y, tileWidth, tileHeight);
  }

  private void drawStringCenter(Graphics g, String s, int XPos, int YPos) {
    Graphics2D g2d = (Graphics2D) g;
    g.setColor(Theme.getForeground());
    int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
    int stringHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
    int offsetWidth = tileWidth / 2 - stringLen / 2;
    int offsetHeight = tileHeight / 2 - stringHeight / 2;
    g2d.drawString(s, offsetWidth + XPos, offsetHeight + YPos);
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
