package puzzles.flow;

import java.util.Collection;

import ai.models.grid.ColorTile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 01.10.2014.
 */
public class FlowCspPuzzle extends SimpleAStarCspPuzzle {


  public FlowCspPuzzle(AStarCsp<ColorTile> astarCsp) {
    super(astarCsp);
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new FlowCspPuzzle(getAstarCsp());
  }

  @Override
  public VariableList generateVariables() {
    VariableList variables = new VariableList();
    Collection<ColorTile> items = getAstarCsp().getAdapter().getItems();
    for (ColorTile colorTile : items) {
      Variable colorVariable = new Variable(colorTile.getId(), getInitialDomain());
      Variable outputVariable = new Variable(colorTile.getOutput(), new Domain(0, 1, 2, 3));
      Variable inputVariable   = new Variable(colorTile.getInput(), new Domain(0, 1, 2, 3));
      if (!colorTile.isEmpty()) {
        colorVariable.setAssumption(colorTile.getInitialValue());
      }
      variables.put(colorVariable);
      variables.put(outputVariable);
      variables.put(inputVariable);
      colorVariable.setListener(colorTile);
    }
    return variables;
  }
}
