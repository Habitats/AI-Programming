package ai.models;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Tile extends Drawable {

  public Tile(int x, int y, State state) {
    super(x, y, state);
  }

  @Override
  public String toString() {
    return String.format("%s: %s, %s", Tile.class.getSimpleName(), x, y);
  }
}
