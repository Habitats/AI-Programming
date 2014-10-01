package puzzles.flow;

import java.util.List;

import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 01.10.2014.
 */
public class FlowCspPuzzle implements AStarCspPuzzle {

  public FlowCspPuzzle(Flow flow) {

  }

  @Override
  public List<Constraint> getConstraints() {
    return null;
  }

  @Override
  public List<Variable> getVariables() {
    return null;
  }

  @Override
  public int getDomainSize() {
    return 0;
  }

  @Override
  public void visualize() {

  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public Variable getVariable(String id) {
    return null;
  }


  @Override
  public List<Variable> generateVariables() {
    return null;
  }

  @Override
  public void setVariables(List<Variable> aVoid) {

  }

  @Override
  public Variable getSuccessor() {
    return null;
  }

  @Override
  public AStarCspPuzzle duplicate() {
    return null;
  }
}
