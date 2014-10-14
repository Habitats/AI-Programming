package puzzles.nono;

import java.util.Collection;
import java.util.List;

import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 01.10.2014.
 */
public class NonoCspPuzzle extends SimpleAStarCspPuzzle {


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

  private List<List<Integer>> getRowSpecs() {
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();
    return items.iterator().next().getRowSpecs();
  }

  private List<List<Integer>> getColSpecs() {
    Collection<NonoTile> items = getAstarCsp().getAdapter().getItems();
    return items.iterator().next().getColSpecs();
  }
}
