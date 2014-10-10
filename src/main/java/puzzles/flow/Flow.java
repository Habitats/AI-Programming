package puzzles.flow;

import org.javatuples.Pair;
import org.javatuples.Tuple;

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
import algorithms.csp.canonical_utils.ExpressionBuilder;
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
    boolean firstNode = true;
    for (ColorTile tile : adapter.getItems()) {
      // if the tile is a predefined tile, it's either a start or endpoint; special constraints apply!
      boolean startOrEndNode = !tile.isEmpty();
      if (startOrEndNode) {
//        String colorExpression = generateAtLeastOneEqualNeighborConstraint(adapter, tile);
//        Constraint colorConstraint = new Constraint(puzzle.getVariables(), colorExpression.trim());
//        constraints.put(colorExpression, colorConstraint);
//        Log.i(TAG, colorConstraint);

//        if (firstNode) {
          String
              inputOutputExpression =
              generateExactlyOneNeighborWithTheSameColorPointsToThisConstraints(adapter, tile);
          Constraint inputOutputConstraint = new Constraint(puzzle.getVariables(), inputOutputExpression.trim());
          constraints.put(inputOutputExpression, inputOutputConstraint);
          Log.i(TAG, inputOutputConstraint);
          firstNode = false;
//        }

      } else {
//        String expression = generateAtLeastTwoEqualNeighborConstraint(adapter, tile);
//        Constraint colorConstraint = new Constraint(puzzle.getVariables(), expression.trim());
//        constraints.put(expression, colorConstraint);
//        Log.i(TAG, colorConstraint);

        String inputOutputExpression = generateExactlyOneNeighborWithTheSameColorPointsToThisConstraints(adapter, tile);
        Constraint inputOutputConstraint = new Constraint(puzzle.getVariables(), inputOutputExpression.trim());
        constraints.put(inputOutputExpression, inputOutputConstraint);
        Log.i(TAG, inputOutputConstraint);

      }

//      generateGraphColoringConstraints(puzzle, (Board<ColorTile>) adapter, constraints, tile);
    }

    List<Constraint> immutableConstraints = Collections.unmodifiableList(new ArrayList<>(constraints.values()));
    setConstraints(immutableConstraints);

    Log.i(TAG, "... finished generating " + constraints.size());

    for (Variable variable : puzzle.getVariables()) {
      variable.setConstraintsContainingVariable(immutableConstraints);
    }

  }


  private String generateExactlyOneNeighborWithTheSameColorPointsToThisConstraints(AIAdapter<ColorTile> adapter,
                                                                                   ColorTile tile) {
    List<ColorTile> neighbors = ((Board) adapter).getManhattanNeighbors(tile);
    Tuple[] pairs = new Tuple[neighbors.size()];
    int i = 0;
    for (ColorTile neighbor : neighbors) {
      // if neighbor is below
      if (neighbor.x == tile.x && neighbor.y < tile.y) {
        pairs[i++] = Pair.with(tile.getInput(), neighbor.getOutput());
      }
      // if neighbor is above
      else if (neighbor.x == tile.x && neighbor.y > tile.y) {
        pairs[i++] = Pair.with(tile.getInput(), "( " + neighbor.getOutput() + " +2 )");
      }
      // if neighbor is left
      else if (neighbor.x < tile.x && neighbor.y == tile.y) {
        pairs[i++] = Pair.with(tile.getInput(), "( " + neighbor.getOutput() + " -1 )");
      }
      // if neighbor is right
      else if (neighbor.x > tile.x && neighbor.y == tile.y) {
        pairs[i++] = Pair.with(tile.getInput(), "( " + neighbor.getOutput() + " +1 )");
      }
//      pairs[i++] = Pair.with(tile.getOutput(), neighbor.getInput());
    }
    // xy.input != xy1.output

    return ExpressionBuilder.exatlyOneTupleEquals(pairs);
  }

  private String generateNoInputAndOneOutputConstraints(AIAdapter<ColorTile> adapter, ColorTile tile) {
    return null;
  }

  private String generateAtLeastTwoEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    Tuple[] pairs = getNeighborIdTuples((Board<ColorTile>) (Board<ColorTile>) adapter, (ColorTile) tile);
    String expression;

    return ExpressionBuilder.atLeastTwoTuplesEquals(pairs);
  }


  private String generateExactlyTwoEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    Tuple[] pairs = getNeighborIdTuples((Board<ColorTile>) adapter, tile);
    String expression;

    return ExpressionBuilder.exatlyTwoTuplesEquals(pairs);
  }

  private Tuple[] getNeighborIdTuples(Board<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = adapter.getManhattanNeighbors(tile);
    String expression = "";

    Tuple[] pairs = new Tuple[neighbors.size()];
    int i = 0;
    for (ColorTile neighbor : neighbors) {
      pairs[i++] = Pair.with(tile.getId(), neighbor.getId());
    }
    return pairs;
  }


  private String generateExactlyOneEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    String expression = "";

    Tuple[] pairs = new Tuple[neighbors.size()];
    int i = 0;
    for (ColorTile neighbor : neighbors) {
      pairs[i++] = Pair.with(tile.getId(), neighbor.getId());
    }

    return ExpressionBuilder.exatlyOneTupleEquals(pairs);
  }


  private String generateAtLeastOneEqualNeighborConstraint(AIAdapter<ColorTile> adapter, ColorTile tile) {
    List<ColorTile> neighbors = ((Board<ColorTile>) adapter).getManhattanNeighbors(tile);
    Tuple[] pairs = new Tuple[neighbors.size()];
    int i = 0;
    for (ColorTile neighbor : neighbors) {
      pairs[i++] = Pair.with(tile.getId(), neighbor.getId());
    }
    return ExpressionBuilder.atLeastOneTupleEquals(pairs);
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

  // GETTERS AND SETTERS

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
