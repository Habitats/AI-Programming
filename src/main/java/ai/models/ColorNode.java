package ai.models;

/**
 * Created by Patrick on 08.09.2014.
 */
public class ColorNode extends Node<ColorNode> {

  private double x;
  private double y;
  private final int index;

  public ColorNode(double x, double y, int index) {
    this.x = x;
    this.y = y;
    this.index = index;
  }

  @Override
  public int compareTo(ColorNode o) {
    return 0;
  }

  public int getX() {
    return (int) (x * 100);
  }

  public int getY() {
    return (int) (y * 100);
  }

  public void normalize(int minX, int minY) {
    x -= minX / 100.;
    y -= minY / 100.;
  }
}
