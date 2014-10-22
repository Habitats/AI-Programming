package puzzles.nono;

import java.util.Collection;
import java.util.List;

import ai.models.grid.Board;
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
  public void setAssumption(String variableId, Object value) {
    ChunkVals vals = ((ChunkVals) value).copy();
    super.setAssumption(variableId, vals);
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
      Variable<NonoDomain> var = new Variable(id, new NonoDomain(row, getAstarCsp().getAdapter().getWidth()));
      variables.add(var);
    }
    for (List<Integer> col : colSpecs) {
      String id = "x" + x++;
      Variable<NonoDomain> var = new Variable(id, new NonoDomain(col, getAstarCsp().getAdapter().getHeight()));
      variables.add(var);
    }

    return variables;
  }

  @Override
  public void visualize() {
    int x = 0;
    int y;
    Variable<ChunkVals> next;
    Board<NonoTile> board = (Board<NonoTile>) getAstarCsp().getAdapter();
    while ((next = getVariable("x" + x)) != null) {
      NonoDomain domain = (NonoDomain) next.getDomain();
      ChunkVals chunkVals = domain.getCertainValues();
      for (y = 0; y < chunkVals.values.size(); y++) {
        board.get(x, y).setColor(chunkVals.values.get(y).ordinal());
      }
      x++;
    }
    board.notifyDataChanged();
  }


  @Override
  public Variable getSuccessor() {
    return super.getSuccessor();
  }

public List<List<Integer>> getRowSpecs() {
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();
    return items.iterator().next().getRowSpecs();
  }

public List<List<Integer>> getColSpecs() {
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();
    return items.iterator().next().getColSpecs();
  }
}
