package ai.models.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ai.models.AIAdapter;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Board<T extends ColorTile & VariableListener<Integer>> extends AIAdapter<T> implements Iterable<List<T>> {

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
    for (int x = 0; x < width; x++) {
      List<T> column = new ArrayList<>(height);
      for (int y = 0; y < height; y++) {
        column.add(y, null);
      }
      tiles.add(column);
    }
  }

  public T get(int x, int y) {
    return tiles.get(x).get(y);
  }

  public void set(T tile) {
    if (hasEmptyTile(tile.x, tile.y)) {
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

  public boolean hasEmptyTile(int x, int y) {
    return (positionExist(x, y) && get(x, y).isEmpty());
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
  public Collection<T> getItems() {
    List<T> items = new ArrayList<>();
    tiles.forEach(items::addAll);
    return items;
  }

  @Override
  public boolean isLegalPosition(T tile) {
    return positionExist(tile.x, tile.y);
  }

  private boolean positionExist(int x, int y) {
    try {
      T colorTile = tiles.get(x).get(y);
      return (colorTile != null);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  public void clear() {
    clear(getWidth(), getHeight());
  }

  public java.util.List<T> getManhattanNeighbors(T tile) {
    java.util.List<T> manhattanNeighbors = new ArrayList<>();
    for (int x = tile.x - 1; x <= tile.x + 1; x++) {
      for (int y = tile.y - 1; y <= tile.y + 1; y++) {
        // if the position is out of bounds, disregard
        if (!positionExist(x, y)) {
          continue;
        }
        // do not put self to its own children
        if (x == tile.x && y == tile.y) {
          continue;
        }
        // disallow diagonal neighbors
        if (tile.x != x && tile.y != y) {
          continue;
        }
        manhattanNeighbors.add(get(x, y));
      }
    }
    return manhattanNeighbors;
  }
}
