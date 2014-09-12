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

  private enum Direction {
    HORIZONTAL, VERTICAL
  }

  private static final String TAG = Sudoku.class.getSimpleName();
  private List<Variable> variables;
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

      List<Variable> horizontal = new ArrayList<>();
      Log.v(TAG, "horzontal: " + getHorizontalConstraintExpression(getVariables(), 0, col, Direction.HORIZONTAL));
      Log.v(TAG, "horzontal: " + getHorizontalConstraintExpression(getVariables(), 0, row, Direction.VERTICAL));
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

  private String getHorizontalConstraintExpression(List<Variable> vars, int i, String rowOrCol, Direction dir) {
    if (i == vars.size()) {
      return "1";
    }
    Variable variable = vars.get(i);
    String s = "";
    int index = (dir == Direction.HORIZONTAL ? 1 : 2);
    for (int j = i + 1; j < vars.size(); j++) {
      if (String.valueOf(vars.get(j).getId().charAt(index)).equals(rowOrCol)) {
        s += variable.getId() + " != " + vars.get(j).getId() + " and ";
      }
    }
    return s + getHorizontalConstraintExpression(vars, i + 1, rowOrCol, dir);
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
        getVariables().get(i).setAssumption(Integer.parseInt(String.valueOf(board.charAt(i))));
      }
    }
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
