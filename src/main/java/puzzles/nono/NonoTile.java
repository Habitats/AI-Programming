package puzzles.nono;

import ai.models.grid.ColorTile;
import puzzles.flow.FlowTile;

/**
 * Created by Patrick on 14.10.2014.
 */
public class NonoTile extends ColorTile{

  private ColorTile output;
  private ColorTile input;
  private String neighborId;
  private FlowTile.State state;

  public NonoTile(int x, int y, int numberOfColors) {
    super(x, y, numberOfColors);
  }

}
