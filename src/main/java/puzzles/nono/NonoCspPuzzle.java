package puzzles.nono;

import java.util.Collection;
import java.util.List;

import ai.Log;
import ai.models.grid.Board;
import ai.models.grid.ColorTile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 01.10.2014.
 */
public class NonoCspPuzzle extends SimpleAStarCspPuzzle {


  private static final String TAG = NonoCspPuzzle.class.getSimpleName();

  public NonoCspPuzzle(AStarCsp<NonoTile> astarCsp) {
    super(astarCsp);
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new NonoCspPuzzle(getAstarCsp());
  }

  @Override
  public VariableList generateVariables() {
    VariableList variables = new VariableList();
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();

    List<List<Integer>> rowSpecs = getRowSpecs();
    List<List<Integer>> colSpecs = getColSpecs();
    int x = 0, y = 0;
    for (List<Integer> row : rowSpecs) {
      String id = "y" + y++;
      Variable<NonoDomain> var = new Variable(id, new NonoDomain(row, getAstarCsp().getAdapter().getHeight()));
      variables.add(var);
    }
    for (List<Integer> col : colSpecs) {
      String id = "x" + x++;
      Variable<NonoDomain> var = new Variable(id, new NonoDomain(col, getAstarCsp().getAdapter().getWidth()));
      variables.add(var);
    }

    x = 0;
    Variable<NonoDomain> next;
    Board<ColorTile> board = (Board<ColorTile>) getAstarCsp().getAdapter();
    while ((next = getVariable("x" + x)) != null) {
      for (y = 0; y < next.getDomain().getSize(); y++) {
        ColorTile tile = board.get(x, y);
        next.addListener(tile);
      }
    }

    return variables;
  }

  public void pruneVariable(Variable<ChunkVals> var) {
    ChunkVals certainValues = ((NonoDomain) var.getDomain()).getCertainValues();
    Log.v(TAG, certainValues);
    Variable<ChunkVals> twin = getTwin(var);
    NonoDomain domainToPrune = (NonoDomain) var.getDomain();
    domainToPrune.pruneDomain(certainValues);
  }

  private Variable<ChunkVals> getTwin(Variable<ChunkVals> var) {
    String id = var.getId();
    String twinId = (id.startsWith("x") ? "y" : "x") + id.substring(1);
    return variables.getVariable(twinId);
  }

  private List<List<Integer>> getRowSpecs() {
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();
    return items.iterator().next().getRowSpecs();
  }

  private List<List<Integer>> getColSpecs() {
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();
    return items.iterator().next().getColSpecs();
  }
}
