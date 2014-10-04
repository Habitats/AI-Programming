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
    int count = 0;
    for (ColorTile tile : adapter.getItems()) {
      // if the tile is a predefined tile, it's either a start or endpoint; special constraints apply!
      String expression;
//      if (!tile.isEmpty()) {
//        expression = generateEndpointConstraintExpression(adapter, tile);
//      } else {
//        expression = generateEndpointConstraintExpression(adapter, tile);
//        expression = generateMidpointConstraintExpression(adapter, tile);
//        continue;
//      }

      for (ColorTile neighbor : ((Board<ColorTile>) adapter).getManhattanNeighbors(tile)) {
        expression = tile.getId() + " != " + neighbor.getId();
        Constraint constraint = new Constraint(puzzle.getVariables(), expression);
        constraints.put(expression, constraint);
        Log.i(TAG, constraint);
        count++;
      }
    }

    List<Constraint> immutableConstraints = Collections.unmodifiableList(new ArrayList<>(constraints.values()));
    setConstraints(immutableConstraints);

    Log.i(TAG, "... finished generating " + constraints.size() + " constraints and filtered out " + (count - constraints
        .size()) + " duplicates!");

    for (Variable variable : puzzle.getVariables()) {
      variable.setConstraintsContainingVariable(immutableConstraints);
    }

  }

  private String generateEndpointConstraintExpression(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";
    for (ColorTile neighbor : neighbors) {
      expression += " or (" + tile.getId() + " == " + neighbor.getId() + ")";
    }
    expression = expression.substring(3);
    return expression;
  }

  private String generateMidpointConstraintExpression(AIAdapter<ColorTile> adapter, ColorTile tile) {
    String id = tile.getId();
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";
    String operator = " e ";
    List<String> pairs = new ArrayList<>();
    for (ColorTile neighbor : neighbors) {
      if (adapter.isLegalPosition(neighbor)) {
        String neighborId = neighbor.getId();
        pairs.add(id + operator + neighborId);
      }
    }
    int numberOfValidNeighbors = pairs.size();
    String finalExpression = "";
    for (int i = 0; i < numberOfValidNeighbors; i++) {
      String subExpression = "";
      for (int j = 0; j < numberOfValidNeighbors; j++) {
        subExpression += " and " + pairs.get(j).replace(operator, j == i ? " == " : " != ");
      }
      subExpression = subExpression.substring(4);
      finalExpression += " or (" + subExpression + ")";
    }
    finalExpression = finalExpression.substring(3);
    return finalExpression;
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
      String[] pairRow = inputList.get(i).split("\\s+");
      int startX = Integer.parseInt(pairRow[1]);
      int startY = Integer.parseInt(pairRow[2]);
      int endX = Integer.parseInt(pairRow[3]);
      int endY = Integer.parseInt(pairRow[4]);

      ColorTile start = new ColorTile(startX, startY, numberOfColors);
      ColorTile end = new ColorTile(endX, endY, numberOfColors);
      start.setInitialValue(i);
      end.setInitialValue(i);
      start.setColor(i, .9);
      end.setColor(i, .9);
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

  @Override
  public void runClicked() {
    super.runClicked();
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
