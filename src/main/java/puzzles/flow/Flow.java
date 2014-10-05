package puzzles.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ai.AIMain;
import ai.Log;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Board;
import ai.models.grid.ColorTile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import puzzles.flow.gui.FlowGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Flow extends AStarCsp<ColorTile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();
  private List<Constraint> constraints;
  private int domainSize;

  @Override
  protected AIGui generateGui() {
    return new FlowGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new FlowCspPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<ColorTile> adapter) {
    Log.v(TAG, "Generating constraints ...");
    HashMap<String, Constraint> constraints = new HashMap<>();
    for (ColorTile tile : adapter.getItems()) {
      // if the tile is a predefined tile, it's either a start or endpoint; special constraints apply!
      String expression;
      if (!tile.isEmpty()) {
        expression = generateAtLeastOneEqualNeighborConstraint(adapter, tile);
      } else {
        expression = generateExactlyTwoEqualNeighborConstraint(adapter, tile);
      }
      Constraint constraint = new Constraint(puzzle.getVariables(), expression.trim());
      constraints.put(expression, constraint);
      Log.i(TAG, constraint);

//      generateGraphColoringConstraints(puzzle, (Board<ColorTile>) adapter, constraints, tile);
    }

    List<Constraint> immutableConstraints = Collections.unmodifiableList(new ArrayList<>(constraints.values()));
    setConstraints(immutableConstraints);

    Log.i(TAG, "... finished generating " + constraints.size());

    for (Variable variable : puzzle.getVariables()) {
      variable.setConstraintsContainingVariable(immutableConstraints);
    }

  }

  private String generateAtLeastTwoEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";
    for (ColorTile neighbor : neighbors) {
      expression += " and (" + tile.getId() + " == " + neighbor.getId() + ")";
    }
    expression = expression.substring(4);

    String[] pairs = expression.split(" and ");
    if (pairs.length == 2) {
      return expression;
    } else if (pairs.length == 3) {
      expression = "(" + pairs[0] + " and " + pairs[1] + ")" //
                   + " or (" + pairs[0] + " and " + pairs[2] + ")"//
                   + " or (" + pairs[1] + " and " + pairs[2] + ")"//
      ;
      return expression;

    } else if (pairs.length == 4) {
      expression = "(" + pairs[0] + " and " + pairs[1] + ")" //
                   + " or (" + pairs[0] + " and " + pairs[2] + ")"//
                   + " or (" + pairs[0] + " and " + pairs[3] + ")"//
                   + " or (" + pairs[1] + " and " + pairs[2] + ")"//
                   + " or (" + pairs[1] + " and " + pairs[3] + ")"//
                   + " or (" + pairs[2] + " and " + pairs[3] + ")"//
      ;
      return expression;
    }
    return "";
  }

  private String generateExactlyTwoEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";
    for (ColorTile neighbor : neighbors) {
      expression += " and (" + tile.getId() + " == " + neighbor.getId() + ")";
    }
    expression = expression.substring(4);

    String[] pairs = expression.split(" and ");
    if (pairs.length == 2) {
      return expression;
    } else if (pairs.length == 3) {
      expression = "(" + pairs[0] + " and " + pairs[1] + " and " + pairs[2].replace("==", "!=") + ")" //
                   + " or (" + pairs[0] + " and " + pairs[2] + " and " + pairs[1].replace("==", "!=") + ")"//
                   + " or (" + pairs[1] + " and " + pairs[2] + " and " + pairs[0].replace("==", "!=") + ")"//
      ;
      return expression;

    } else if (pairs.length == 4) {
      expression = "(" + pairs[0] + " and " + pairs[1] + " and " +  //
                   pairs[2].replace("==", "!=") + " and " + pairs[3].replace("==", "!=") + ")" //

                   + " or (" + pairs[0] + " and " + pairs[2] + " and " //
                   + pairs[1].replace("==", "!=") + " and " + pairs[3].replace("==", "!=") + ")"//

                   + " or (" + pairs[0] + " and " + pairs[3] + " and " //
                   + pairs[1].replace("==", "!=") + " and " + pairs[2].replace("==", "!=") + ")"//

                   + " or (" + pairs[1] + " and " + pairs[2] + " and " //
                   + pairs[0].replace("==", "!=") + " and " + pairs[3].replace("==", "!=") + ")"//

                   + " or (" + pairs[1] + " and " + pairs[3] + " and "  //
                   + pairs[0].replace("==", "!=") + " and " + pairs[2].replace("==", "!=") + ")"//

                   + " or (" + pairs[2] + " and " + pairs[3] + " and "  //
                   + pairs[0].replace("==", "!=") + " and " + pairs[1].replace("==", "!=") + ")"//
      ;
      return expression;
    }
    return "";
  }

  private void generateGraphColoringConstraints(AStarCspPuzzle puzzle, Board<ColorTile> adapter,
                                                HashMap<String, Constraint> constraints, ColorTile tile) {
    String expression;
    for (ColorTile neighbor : adapter.getManhattanNeighbors(tile)) {
      expression = tile.getId() + " != " + neighbor.getId();
      Constraint constraint = new Constraint(puzzle.getVariables(), expression);
      constraints.put(expression, constraint);
      Log.i(TAG, constraint);
    }
  }


  private String generateExactlyOneEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";
    for (ColorTile neighbor : neighbors) {
      expression += " or (" + tile.getId() + " == " + neighbor.getId() + ")";
    }
    expression = expression.substring(3);
    return expression;
  }

  private String generateAtLeastOneEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";
    for (ColorTile neighbor : neighbors) {
      expression += " or (" + tile.getId() + " == " + neighbor.getId() + ")";
    }
    expression = expression.substring(3);
    return expression;
  }


  @Override
  protected AIAdapter<ColorTile> generateAdapter(String input) {
    List<String> inputList = Arrays.asList(input.split("\\n"));
    int dimension = Integer.parseInt(inputList.get(0).split("\\s+")[0]);
    int numberOfColors = Integer.parseInt(inputList.get(0).split("\\s+")[1]);

    Board<ColorTile> board = new Board(dimension, dimension);
    for (int x = 0; x < dimension; x++) {
      for (int y = 0; y < dimension; y++) {
        board.set(new ColorTile(x, y, numberOfColors));
      }
    }

    for (int i = 1; i < inputList.size(); i++) {
      int value = i - 1;
      String[] pairRow = inputList.get(i).split("\\s+");
      int startX = Integer.parseInt(pairRow[1]);
      int startY = Integer.parseInt(pairRow[2]);
      int endX = Integer.parseInt(pairRow[3]);
      int endY = Integer.parseInt(pairRow[4]);

      ColorTile start = new ColorTile(startX, startY, numberOfColors);
      ColorTile end = new ColorTile(endX, endY, numberOfColors);
      start.setInitialValue(value);
      end.setInitialValue(value);
      start.setColor(value, .9);
      end.setColor(value, .9);
      board.set(start);
      board.set(end);
    }
    board.notifyDataChanged();
    setDomainSize(numberOfColors);
    return board;
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= FlowUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(FlowUtils.samples.get(i));
  }

  @Override
  public List<Constraint> getConstraints() {
    return constraints;
  }


  public void setConstraints(List<Constraint> constraints) {
    this.constraints = constraints;
  }

  public void setDomainSize(int domainSize) {
    this.domainSize = domainSize;
  }

  @Override
  public int getDomainSize() {
    return domainSize;
  }
}
