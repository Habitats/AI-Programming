package puzzles.sudoku;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import ai.Log;
import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 12.09.2014.
 */
public class Sudoku implements CspPuzzle {

  private enum Direction {
    HORIZONTAL, VERTICAL
  }

  private static final String TAG = Sudoku.class.getSimpleName();
  private VariableList variables;
  private List<Constraint> constraints;

  public Sudoku() {
    setVariables();
    loadHardBoard();
    setConstraints();
    Log.v(TAG, "finished creating constraints");
  }

  private void setConstraints() {
    constraints = new ArrayList<>();
    for (Variable var : getVariables()) {
      String col = String.valueOf(var.getId().charAt(1));
      String row = String.valueOf(var.getId().charAt(2));
      String expression = "";

//      Log.v(TAG, "horzontal: " + getHorizontalConstraintExpression(getVariablesMap(), 0, row));
//      Log.v(TAG, "vertical:  " + getVerticalConstraintExpression(getVariablesMap(), 0, col));
      expression =
          String.format(
              "v%s1 != v%s2 and v%s1 != v%s3 and v%s1 != v%s4 and v%s3 != v%s4 and v%s2 != v%s3 and v%s2 != v%s4", //
              col, col, col, col, col, col, col, col, col, col, col, col);
      Constraint c1 = new Constraint(getVariables(), expression);

      expression =
          String.format(
              "v1%s != v2%s and v1%s != v3%s and v1%s != v4%s and v3%s != v4%s and v2%s != v3%s and v2%s != v4%s", //
              row, row, row, row, row, row, row, row, row, row, row, row);
      Constraint c2 = new Constraint(getVariables(), expression);
      constraints.add(c1);
      constraints.add(c2);
    }
  }

  private String getVerticalConstraintExpression(List<Variable> vars, int i, String col) {
    if (i == vars.size()) {
      return "1";
    }
    Variable variable = vars.get(i);
    String s = "";
    int width = (int) Math.sqrt(vars.size());
    for (int j = i + 1; j < vars.size(); j++) {
      Variable var = vars.get(j);
      String toCheck = String.valueOf(var.getId().charAt(2));
      if (toCheck.equals(col)) {
        s += variable.getId() + " != " + var.getId() + " and ";
      }
    }
    return s + getVerticalConstraintExpression(vars, i + width, col);
  }

  private String getHorizontalConstraintExpression(List<Variable> vars, int i, String row) {
    if (i == vars.size()) {
      return "1";
    }
    Variable variable = vars.get(i);
    String s = "";
    for (int j = i + 1; j < vars.size(); j++) {
      Variable var = vars.get(j);
      String toCheck = String.valueOf(var.getId().charAt(1));
      if (toCheck.equals(row)) {
        s += variable.getId() + " != " + var.getId() + " and ";
      }
    }
    return s + getHorizontalConstraintExpression(vars, i + 1, row);
  }

  private void setVariables() {
    int[] domain = new int[4];
    for (int i = 1; i <= domain.length; i++) {
      domain[i - 1] = i;
    }
    variables = new VariableList();
    for (int x = 1; x <= domain.length; x++) {
      for (int y = 1; y <= domain.length; y++) {
        final Variable var = new Variable("v" + x + y, new Domain(domain));
        var.setListener(new VariableListener<Integer>() {
          @Override
          public void onValueChanged(Integer value, int size) {
            Log.v(TAG, "value of: " + var.getId() + ", changed to: " + value);
          }

          @Override
          public void onAssumptionMade(Integer value) {
            Log.v(TAG, "assumption of: " + var.getId() + ", changed to: " + value);
          }

          @Override
          public void onDomainChanged(Domain domain) {

          }

          @Override
          public boolean isEmpty() {
            return false;
          }

          @Override
          public int getInitialValue() {
            return 0;
          }
        });
        getVariables().add(var);
      }
    }
  }

  private void loadEasyBoard() {
    String board = "3040" + "0102" + "0403" + "2010";
    loadFromString(board);

    visualize();
  }

  private void loadMediumBoard() {
    String board = "0130" + "0004" + "0001" + "0240";
    loadFromString(board);

    visualize();
  }

  private void loadHardBoard() {
    String board = "0200" + "1020" + "2030" + "0300";
    board = "4000 0201 0402 2000";
    board = "0001001010020030";
    loadFromString(board);

    visualize();
  }

  /**
   * @param board in format: 0000 0000 0000 0000
   */
  private void loadFromString(String board) {
    board = board.replaceAll("\\s", "");
    for (int i = 0; i < getVariables().size(); i++) {
      if (!String.valueOf(board.charAt(i)).equals("0")) {
        throw new NotImplementedException();
      }
    }
  }

  @Override
  public List<Constraint> getConstraints() {
    return constraints;
  }

  @Override
  public VariableList getVariables() {
    return variables;
  }

  @Override
  public int getDomainSize() {
    return 0;
  }

  @Override
  public void visualize() {
    printSudoku();
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


  private void printSudoku() {
    String row = "";
    throw new NotImplementedException();

  }
}
