package puzzles.nono;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ai.Log;
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

    return variables;
  }

  public void pruneVariable(Variable<ChunkVals> var, String incomingAxis, String twinAxis) {
    ChunkVals certainValues = ((NonoDomain) var.getDomain()).getCertainValues();
    Log.v(TAG, certainValues);
    // 0 1 1 1 0 0
    // 0 1 0 1 0 0
    // 0 1 0 1 0 0
    // 0 1 1 1 0 0
    // 0 0 0 1 0 0

    if (var.getId().startsWith(incomingAxis)) {
      int y = 0;
      int x = Integer.parseInt(var.getId().substring(1));
      for (int certainIndex = 0; certainIndex < certainValues.values.size(); certainIndex++) {
        int certainValue = certainValues.values.get(certainIndex);
        if (certainValue == 1) {
          Variable<ChunkVals> twin = getVariable(twinAxis + certainIndex);
          NonoDomain domain = (NonoDomain) twin.getDomain();
          Iterator<ChunkVals> iterator = domain.iterator();
          while (iterator.hasNext()) {
            ChunkVals vals = iterator.next();
            if (vals.values.get(certainIndex) != 1) {
              iterator.remove();
            }
          }
        }
      }
    }

//    Variable<ChunkVals> twin = getTwin(var);
//    NonoDomain domainToPrune = (NonoDomain) var.getDomain();
//    domainToPrune.pruneDomain(certainValues);
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
