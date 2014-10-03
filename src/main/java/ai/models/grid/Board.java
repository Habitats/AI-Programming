package ai.models.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ai.models.AIAdapter;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Board extends AIAdapter<Tile> implements Iterable<List<Tile>> {

  private List<List<Tile>> tiles;
  private Tile start;
  private Tile goal;

  public void set(int x, int y, int widthSpan, int heightSpan, Tile.State state) {
    for (int w = 0; w < widthSpan; w++) {
      for (int h = 0; h < heightSpan; h++) {
        set(x + w, y + h, state);
      }
    }
  }

  public void set(int x, int y, Tile.State state) {
    Tile tile = new Tile(x, y, state);
    set(tile);
  }

  public void initEmptyBoard(int width, int height) {
    setWidth(width);
    setHeight(height);
    tiles = new ArrayList<>();
    for (int x = 0; x < width; x++) {
      List<Tile> column = new ArrayList<>();
      for (int y = 0; y < height; y++) {
        column.add(new Tile(x, y, Tile.State.EMPTY));
      }
      tiles.add(column);
    }
    notifyDataChanged();
  }

  public void clear() {
    initEmptyBoard(getWidth(), getHeight());
  }

  public Tile get(int x, int y) {
    return tiles.get(x).get(y);
  }

  public void set(Tile tile) {
    tiles.get(tile.x).get(tile.y).setState(tile.getState());
  }


  @Override
  public Iterator<List<Tile>> iterator() {
    return tiles.iterator();
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
      Tile tile = tiles.get(x).get(y);
      return tile.isEmpty();
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  @Override
  public String toString() {
    return "Width: " + getWidth() + " Height: " + getHeight();
  }

  @Override
  public Tile getItem(int index) {
    return null;
  }

  @Override
  public int getSize() {
    return getWidth() * getHeight();
  }

  @Override
  public Collection getItems() {
    List<Tile> items = new ArrayList<>();
    tiles.forEach(items::addAll);
    return items;
  }
}
