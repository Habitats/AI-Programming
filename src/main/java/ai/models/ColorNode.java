package ai.models;

import algorithms.a_star.AStarNode;

/**
 * Created by Patrick on 08.09.2014.
 */
public class ColorNode extends Node {

  private final double x;
  private final double y;
  private final int index;

  public ColorNode(double x, double y, int index) {
    this.x = x;
    this.y = y;
    this.index = index;
  }

  @Override
  public int compareTo(AStarNode o) {
    return 0;
  }

  public int getX() {
    return (int) (x * 1);
  }

  public int getY() {
    return (int) (y * 1);
  }
}
