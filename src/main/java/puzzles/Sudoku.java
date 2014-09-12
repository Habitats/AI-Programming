package puzzles;

import java.util.ArrayList;
import java.util.List;

import ai.Log;
import algorithms.csp.Constraint;
import algorithms.csp.CspPuzzle;
import algorithms.csp.Domain;
import algorithms.csp.Variable;

/**
 * Created by Patrick on 12.09.2014.
 */
public class Sudoku implements CspPuzzle {

  private static final String TAG = Sudoku.class.getSimpleName();
  private List<Variable> variables;
  private List<Constraint> constraints;

  public Sudoku() {
    setVariables();
    loadBoard();
    setConstraints();
    Log.v(TAG, "finished creating constraints");
  }

  private void setConstraints() {
    constraints = new ArrayList<>();
    for (Variable var : getVariables()) {
      String col = String.valueOf(var.getId().charAt(1));
      String row = String.valueOf(var.getId().charAt(2));
      Constraint
          c1 =
          new Constraint(getVariables(), String.format(
              "v%s1 != v%s2 and v%s1 != v%s3 and v%s1 != v%s4 and v%s3 != v%s4 and v%s2 != v%s3 and v%s2 != v%s4", //
              col, col, col, col, col, col, col, col, col, col, col, col));
      Constraint
          c2 =
          new Constraint(getVariables(), String.format(
              "v1%s != v2%s and v1%s != v3%s and v1%s != v4%s and v3%s != v4%s and v2%s != v3%s and v2%s != v4%s", //
              row, row, row, row, row, row, row, row, row, row, row, row));
      constraints.add(c1);
      constraints.add(c2);
    }
  }

  private void setVariables() {
    int[] domain = new int[4];
    for (int i = 1; i <= domain.length; i++) {
      domain[i - 1] = i;
    }
    variables = new ArrayList<>();
    for (int x = 1; x <= domain.length; x++) {
      for (int y = 1; y <= domain.length; y++) {
        Variable var = new Variable("v" + x + y, new Domain(domain));
        getVariables().add(var);
      }
    }
  }

  private void loadBoard() {
    getVariables().get(0).setValue(3);
    getVariables().get(2).setValue(4);
    getVariables().get(5).setValue(1);
    getVariables().get(7).setValue(2);
    getVariables().get(9).setValue(4);
    getVariables().get(11).setValue(3);
    getVariables().get(12).setValue(2);
    getVariables().get(14).setValue(1);
    getVariables().get(15).setValue(4);
  }

  @Override
  public List<Constraint> getConstraints() {
    return constraints;
  }

  @Override
  public List<Variable> getVariables() {
    return variables;
  }

  @Override
  public void visualize() {
    printSudoku();
  }

  private void printSudoku() {
    String row = "";
    for (Variable v : getVariables()) {
      row += v.getValue();
      if (((getVariables().indexOf(v) + 1) % 4) == 0) {
        Log.v(TAG, row);
        row = "";
      }
    }
  }
}
