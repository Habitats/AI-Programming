package ai.models.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ai.models.AIAdapter;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Board<T extends Tile> extends AIAdapter<T> implements Iterable<List<T>> {

  private List<List<T>> tiles;
  private T start;
  private T goal;

  public Board(int width, int height) {
    clear(width, height);
  }

  private void clear(int width, int height) {
    setWidth(width);
    setHeight(height);
    tiles = new ArrayList<>();
    for (int h = 0; h < height; h++) {
      List<T> column = new ArrayList<>(width);
      for (int w = 0; w < width; w++) {
        column.add(w, null);
      }
      tiles.add(column);
    }
  }

  public T get(int x, int y) {
    return tiles.get(x).get(y);
  }

  public void set(T tile) {
    if (hasAvailableTile(tile.x, tile.y)) {
      tiles.get(tile.x).get(tile.y).update(tile);
    } else {
      tiles.get(tile.x).set(tile.y, tile);
    }
  }


  @Override
  public Iterator<List<T>> iterator() {
    return tiles.iterator();
  }

  public Board setGoal(T goal) {
    this.goal = goal;
    set(goal);
    return this;
  }

  public Board setStart(T start) {
    this.start = start;
    set(start);
    return this;
  }

  public T getStart() {
    return start;
  }

  public T getGoal() {
    return goal;
  }

  public boolean hasAvailableTile(int x, int y) {
    try {
      Tile tile = tiles.get(x).get(y);
      // if not null AND is empty, return true
      return (tile != null && tile.isEmpty());
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  @Override
  public String toString() {
    return "Width: " + getWidth() + " Height: " + getHeight();
  }

  @Override
  public T getItem(int index) {
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

  public void clear() {
    clear(getWidth(), getHeight());
  }
}
