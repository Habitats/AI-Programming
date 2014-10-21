package puzzles.sudoku;

import java.util.ArrayList;
import java.util.List;

import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.CanonicalConstraint;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 12.09.2014.
 */
public class TestGACProblem implements CspPuzzle {

  private final int i;
  private List<Constraint> constraints;
  private VariableList variables;

  public TestGACProblem(int i) {
    this.i = i;
    setVariables();
    setConstraints();
  }


  private void setVariables() {
    variables = new VariableList();
    if (i == 1) {
      Variable x = new Variable("x", new Domain(0, 1, 2, 3, 4, 5, 6));
      Variable y = new Variable("y", new Domain(0, 1, 2, 3, 4, 5, 6));
      Variable z = new Variable("z", new Domain(0, 1, 2, 3, 4, 5, 6));

      variables.add(x);
      variables.add(y);
      variables.add(z);
    } else if (i == 2) {
      Variable x = new Variable("x", new Domain(1, 2, 3, 4, 5));
      Variable y = new Variable("y", new Domain(1, 2, 3, 4, 5));
      Variable z = new Variable("z", new Domain(1, 2, 3, 4, 5));
      Variable w = new Variable("w", new Domain(1, 2, 3, 4, 5));

      variables.add(x);
      variables.add(y);
      variables.add(z);
      variables.add(w);
    }
  }

  private void setConstraints() {
    constraints = new ArrayList<>();
    if (i == 1) {
      Constraint c1 = new CanonicalConstraint(variables, "x == 2*y");
      Constraint c2 = new CanonicalConstraint(variables, "x > z");
      Constraint c3 = new CanonicalConstraint(variables, "y < z");
      Constraint c4 = new CanonicalConstraint(variables, "(y +x + z )== 9");
      constraints.add(c1);
      constraints.add(c2);
      constraints.add(c3);
      constraints.add(c4);
    } else if (i == 2) {
      Constraint c1 = new CanonicalConstraint(variables, "x+w +y< z+1");
      constraints.add(c1);
    }
  }

  @Override
  public List<Constraint> getConstraints() {
    return constraints;
  }

  @Override
  public algorithms.csp.canonical_utils.VariableList getVariables() {
    return variables;
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
  public algorithms.csp.canonical_utils.VariableList generateVariables() {
    return null;
  }

  @Override
  public void setVariables(VariableList aVoid) {

  }

  @Override
  public void setAssumption(String id, Object value) {
    getVariable(id).setAssumption(value);
  }

}
