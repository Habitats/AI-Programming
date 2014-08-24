package a_star.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import a_star.interfaces.BoardListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Board implements Iterable<List<Tile>> {

  public final int width;
  public final int height;
  private BoardListener listener;
  private List<List<Tile>> board;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void set(int x, int y, int width, int height, Tile.State state) {
    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        Tile tile = new Tile(x + w, y + h, state);
        set(tile);
      }
    }
  }

  private void initEmptyBoard(int width, int height) {
    board = new ArrayList<List<Tile>>();
    for (int x = 0; x < width; x++) {
      List<Tile> column = new ArrayList<Tile>();
      for (int y = 0; y < height; y++) {
        column.add(new Tile(x, y, Tile.State.EMPTY));
      }
      board.add(column);
    }
    listener.notifyDataChanged();
  }

  public void clear() {
    initEmptyBoard(width, height);
  }

  public Tile get(int x, int y) {
    return board.get(x).get(y);
  }

  public void set(Tile tile) {
    board.get(tile.x).get(tile.y).setState(tile.getState());
    listener.notifyDataChanged();
  }

  public void setListener(BoardListener listener) {
    this.listener = listener;
  }

  @Override
  public Iterator<List<Tile>> iterator() {
    return board.iterator();
  }
}
