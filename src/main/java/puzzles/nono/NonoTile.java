package puzzles.nono;

import java.util.List;

import ai.models.grid.ColorTile;

/**
 * Created by Patrick on 14.10.2014.
 */
public class NonoTile extends ColorTile {


  private final List<List<Integer>> colSpecs;
  private final List<List<Integer>> rowSpecs;

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
