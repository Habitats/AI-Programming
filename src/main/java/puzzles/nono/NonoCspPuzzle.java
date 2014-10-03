package puzzles.nono;

import java.util.List;

import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 01.10.2014.
 */
public class NonoCspPuzzle implements AStarCspPuzzle {

  public NonoCspPuzzle(Nono nono) {

  }

  @Override
  public List<Constraint> getConstraints() {
    return null;
  }

  @Override
  public VariableList getVariables() {
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
  public VariableList generateVariables() {
    return null;
  }

  @Override
  public void setVariables(VariableList aVoid) {

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
