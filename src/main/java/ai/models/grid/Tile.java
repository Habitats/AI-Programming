package ai.models.grid;

import java.awt.*;

import ai.models.Node;

/**
 * Created by Patrick on 24.08.2014.
 */
public abstract class Tile extends Node<Node> {

  public final int x;
  public final int y;
  private String text;

  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("%s: %s, %s - Empty: %s", Tile.class.getSimpleName(), x, y, isEmpty());
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    if (text == null) {
      return toString();
    }
    return text;
  }

  @Override
  public int compareTo(Node o) {
    return 0;
  }

  public abstract boolean isEmpty();

  public abstract void update(Tile tile);

  public abstract Color getColor();
}
