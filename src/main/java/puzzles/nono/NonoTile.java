package puzzles.nono;

import java.util.List;

import ai.models.grid.CspColorTile;
import puzzles.flow.FlowTile;

/**
 * Created by Patrick on 14.10.2014.
 */
public class NonoTile extends CspColorTile {


  private final List<List<Integer>> colSpecs;
  private final List<List<Integer>> rowSpecs;
  private CspColorTile output;
  private CspColorTile input;
  private String neighborId;
  private FlowTile.State state;

  public NonoTile(int x, int y, int numberOfColors, List<List<Integer>> colSpecs, List<List<Integer>> rowSpecs) {
    super(x, y, numberOfColors);
    this.colSpecs = colSpecs;
    this.rowSpecs = rowSpecs;
  }

  public List<List<Integer>> getRowSpecs() {
    return rowSpecs;
  }

  public List<List<Integer>> getColSpecs() {
    return colSpecs;
  }
}
