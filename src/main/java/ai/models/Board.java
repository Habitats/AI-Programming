package ai.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import puzzles.shortestpath.interfaces.BoardListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Board implements Iterable<List<Tile>> {

  public final int width;
  public final int height;
  private BoardListener listener;
  private List<List<Tile>> board;
  private Tile start;
  private Tile goal;

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
    notifyDataChanged();
  }

  public void clear() {
    initEmptyBoard(width, height);
  }

  public Tile get(int x, int y) {
    return board.get(x).get(y);
  }

  public void set(Tile tile) {
    board.get(tile.x).get(tile.y).setState(tile.getState());
  }

  public void notifyDataChanged() {
    if (listener != null) {
      listener.notifyDataChanged();
    }
  }

  public void setListener(BoardListener listener) {
    this.listener = listener;
  }

  @Override
  public Iterator<List<Tile>> iterator() {
    return board.iterator();
  }

  public Board setGoal(Tile goal) {
    this.goal = goal;
    set(goal);
    return this;
  }

  public Board setStart(Tile start) {
    this.start = start;
    set(start);
    return this;
  }

  public Tile getStart() {
    return start;
  }

  public Tile getGoal() {
    return goal;
  }

  public boolean hasTile(int x, int y) {
    try {
      Tile tile = board.get(x).get(y);
      return tile.getState() != Tile.State.OBSTICLE;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  @Override
  public String toString() {
    return "Width: " + width + " Height: " + height;
  }
}
