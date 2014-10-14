package puzzles.flow;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.AIMain;
import ai.Log;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Board;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import algorithms.csp.canonical_utils.CanonicalConstraint;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import puzzles.flow.gui.FlowGui;

import static algorithms.csp.canonical_utils.ExpressionBuilder.E;
import static algorithms.csp.canonical_utils.ExpressionBuilder.OR;
import static algorithms.csp.canonical_utils.ExpressionBuilder.S;
import static algorithms.csp.canonical_utils.ExpressionBuilder.is;
import static algorithms.csp.canonical_utils.ExpressionBuilder.not;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Flow extends AStarCsp<FlowTile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();

  private List<Constraint> constraints;
  private int domainSize;

  @Override
  protected AIGui generateGui() {
    return new FlowGui();
  }

  public void test() {
    loadClicked();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new FlowCspPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<FlowTile> adapter) {
    Log.v(TAG, "Generating constraints ...");
    HashMap<String, Constraint> constraints = new HashMap<>();

    for (FlowTile tile : adapter.getItems()) {
      // if the tile is a predefined tile, it's either a start or endpoint; special constraints apply!
      if (tile.getColorState() != FlowTile.State.MID) {
        // if start state, only output
        if (tile.getColorState() == FlowTile.State.START) {
          addOutputSameColorConstraints(puzzle, constraints, tile);
        }
        // if end state, only input
        else if (tile.getColorState() == FlowTile.State.END) {
          addInputSameColorConstraints(puzzle, constraints, tile);
        }

      } else {
        addOutputSameColorConstraints(puzzle, constraints, tile);
        addInputSameColorConstraints(puzzle, constraints, tile);

        // this one is pretty solid
        String inputNotOutputExpression = generateInputNotOutputConstraint(adapter, tile).trim();
        Constraint inputNotOutputConstraint = new CanonicalConstraint(puzzle.getVariables(), inputNotOutputExpression);
        putConstraint(constraints, inputNotOutputExpression, inputNotOutputConstraint);
        Log.i(TAG, inputNotOutputConstraint);
      }
    }

    for (FlowTile tile : adapter.getItems()) {
      Map<Integer, FlowTile> neighbors = tile.getManhattanNeighbors();
      for (Integer neighborIndex : neighbors.keySet()) {
        FlowTile neighbor = neighbors.get(neighborIndex);
        Map<Integer, FlowTile> adjNeighbors = neighbor.getManhattanNeighbors();
        for (Integer adjNeighborIndex : adjNeighbors.keySet()) {
          if (neighborIndex == (adjNeighborIndex + 2) % 4) {
            continue;
          }
          FlowTile adjNeighbor = adjNeighbors.get(adjNeighborIndex);

          if (tile.getColorState() == FlowTile.State.MID && adjNeighbor.getColorState() == FlowTile.State.MID) {
            addSingleInputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
            addSingleOutputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
          } else {
            // if both are start state, only output
            if (tile.getColorState() == FlowTile.State.START && adjNeighbor.getColorState() == FlowTile.State.START) {
              addSingleOutputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
            }
            // if borth are end state, only input
            else if (tile.getColorState() == FlowTile.State.END && adjNeighbor.getColorState() == FlowTile.State.END) {
              addSingleInputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
            }
            // if tile is mid
            else if (tile.getColorState() == FlowTile.State.MID) {
              // is start, only output
              if (adjNeighbor.getColorState() == FlowTile.State.START) {
                addSingleOutputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
              } else {
                addSingleInputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
              }
            }
            // if adj is mid
            else if (adjNeighbor.getColorState() == FlowTile.State.MID) {
              if (tile.getColorState() == FlowTile.State.START) {
                addSingleOutputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
              } else {
                addSingleInputConstraint(puzzle, constraints, tile, neighborIndex, adjNeighborIndex, adjNeighbor);
              }
            }
          }
        }
      }
    }

    List<Constraint> immutableConstraints = Collections.unmodifiableList(new ArrayList<>(constraints.values()));
    setConstraints(immutableConstraints);

    Log.i(TAG, "... finished generating " + constraints.size());

    for (Variable variable : puzzle.getVariables()) {
      variable.setConstraintsContainingVariable(immutableConstraints);
    }
  }

  private void addInputSameColorConstraints(AStarCspPuzzle puzzle, HashMap<String, Constraint> constraints,
                                            FlowTile tile) {
    Map<Integer, FlowTile> neighbors = tile.getManhattanNeighbors();
    for (Integer index : neighbors.keySet()) {
      FlowTile neighbor = neighbors.get(index);
      String expression = //
          S + not(Pair.with(tile.getInput(), index)) + OR + is(Pair.with(tile.getId(), neighbor.getId())) + E;
      Constraint outputColorConstraint = new CanonicalConstraint(puzzle.getVariables(), expression);
      putConstraint(constraints, expression, outputColorConstraint);
    }
  }

  private void addOutputSameColorConstraints(AStarCspPuzzle puzzle, HashMap<String, Constraint> constraints,
                                             FlowTile tile) {
    Map<Integer, FlowTile> neighbors = tile.getManhattanNeighbors();
    for (Integer index : neighbors.keySet()) {
      FlowTile neighbor = neighbors.get(index);
      String expression = //
          S + not(Pair.with(tile.getOutput(), index)) + OR + is(Pair.with(tile.getId(), neighbor.getId())) + E;
      Constraint outputColorConstraint = new CanonicalConstraint(puzzle.getVariables(), expression);
      putConstraint(constraints, expression, outputColorConstraint);
    }
  }

  private Constraint putConstraint(HashMap<String, Constraint> constraints, String inputNotOutputExpression,
                                   Constraint inputNotOutputConstraint) {
    return constraints.put(inputNotOutputExpression, inputNotOutputConstraint);
  }

  private void addSingleInputConstraint(AStarCspPuzzle puzzle, HashMap<String, Constraint> constraints, FlowTile tile,
                                        Integer neighborIndex, Integer adjNeighborIndex, FlowTile adjNeighbor) {
    String expression = S + not(Pair.with(tile.getInput(), neighborIndex)) + OR +
                        not(Pair.with(adjNeighbor.getInput(), (adjNeighborIndex + 2) % 4)) + E;
    Constraint inputConstraint = new CanonicalConstraint(puzzle.getVariables(), expression);
    putConstraint(constraints, expression, inputConstraint);
    Log.i(TAG, inputConstraint);
  }

  private void addSingleOutputConstraint(AStarCspPuzzle puzzle, HashMap<String, Constraint> constraints, FlowTile tile,
                                         Integer neighborIndex, Integer adjNeighborIndex, FlowTile adjNeighbor) {
    String outExpression = S + not(Pair.with(tile.getOutput(), neighborIndex)) + OR +
                           not(Pair.with(adjNeighbor.getOutput(), (adjNeighborIndex + 2) % 4)) + E;
    Constraint outputConstraint = new CanonicalConstraint(puzzle.getVariables(), outExpression);
    putConstraint(constraints, outExpression, outputConstraint);
    Log.i(TAG, outputConstraint);
  }

  private String generateInputNotOutputConstraint(AIAdapter<FlowTile> adapter, FlowTile tile) {
    return not(Pair.with(tile.getInput(), tile.getOutput()));
  }

  @Override
  protected AIAdapter<FlowTile> generateAdapter(String input) {
    List<String> inputList = Arrays.asList(input.split("\\n"));
    int dimension = Integer.parseInt(inputList.get(0).split("\\s+")[0]);
    int numberOfColors = Integer.parseInt(inputList.get(0).split("\\s+")[1]);

    Board<FlowTile> board = new Board(dimension, dimension);
    for (int x = 0; x < dimension; x++) {
      for (int y = 0; y < dimension; y++) {
        FlowTile tile = new FlowTile(x, y, numberOfColors);
        board.set(tile);
      }
    }
    for (FlowTile tile : board.getItems()) {
      tile.setManhattanNeighbors(board.getManhattanNeighbors(tile));
    }

    for (int i = 1; i < inputList.size(); i++) {
      int value = i - 1;
      String[] pairRow = inputList.get(i).split("\\s+");
      int startX = Integer.parseInt(pairRow[1]);
      int startY = Integer.parseInt(pairRow[2]);
      int endX = Integer.parseInt(pairRow[3]);
      int endY = Integer.parseInt(pairRow[4]);

      FlowTile start = new FlowTile(startX, startY, numberOfColors);
      FlowTile end = new FlowTile(endX, endY, numberOfColors);
      start.setInitialValue(value);
      start.setColorState(FlowTile.State.START);
      end.setInitialValue(value);
      end.setColorState(FlowTile.State.END);
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
  public AStarCspPuzzle getSamplePuzzle(int i) {
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
