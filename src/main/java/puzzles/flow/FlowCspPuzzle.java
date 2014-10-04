package puzzles.flow;

import java.util.List;

import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 01.10.2014.
 */
public class FlowCspPuzzle extends SimpleAStarCspPuzzle {

  private final Flow flow;

  public FlowCspPuzzle(Flow flow) {
    this.flow = flow;
  }


  @Override
  public AStarCspPuzzle duplicate() {
    return null;
  }

  @Override
  public List<Constraint> getConstraints() {
    return null;
  }

  @Override
  public void visualize() {

  }

  @Override
  public VariableList generateVariables() {
    return null;
  }
}
