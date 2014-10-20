package puzzles.nono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Board;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import algorithms.csp.GeneralArchConsistency;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import puzzles.nono.gui.NonoGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Nono extends AStarCsp<NonoTile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();
  private List<Constraint> constraints;

  @Override
  protected AIGui generateGui() {
    return new NonoGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new NonoCspPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<NonoTile> board) {

  }

  @Override
  protected AIAdapter<NonoTile> generateAdapter(String input) {
    List<String> inputList = Arrays.asList(input.split("\\n"));
    int width = Integer.parseInt(inputList.get(0).split("\\s+")[0]);
    int height = Integer.parseInt(inputList.get(0).split("\\s+")[1]);
    int numberOfColors = 3;

    List<List<Integer>> rowSpecs = new ArrayList<>();
    List<List<Integer>> colSpecs = new ArrayList<>();

    for (int i = 1; i < height + 1; i++) {
      String[] s = inputList.get(i).split("\\s+");
      List<Integer> args = new ArrayList<>();
      for (String n : s) {
        args.add(Integer.parseInt(n));
      }
      rowSpecs.add(args);
    }
    for (int i = height + 1; i < height + width+1; i++) {
      String[] s = inputList.get(i).split("\\s+");
      List<Integer> args = new ArrayList<>();
      for (String n : s) {
        args.add(Integer.parseInt(n));
      }
      Collections.reverse(args);
      colSpecs.add(args);
    }

    Board<NonoTile> board = new Board(width, height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        NonoTile tile = new NonoTile(x, y, numberOfColors, colSpecs, rowSpecs);
        board.set(tile);
      }
    }

    return board;
  }

  public void test(int i) {
    NonoCspPuzzle puzzle = (NonoCspPuzzle) getSamplePuzzle(i);
    GeneralArchConsistency.printVariables(puzzle);
    for (Variable var : puzzle.getVariables()) {
      String incomingAxis = String.valueOf(var.getId().charAt(0));
      String twinAxis = incomingAxis.equals("x") ? "y" : "x";
      puzzle.pruneVariable(var, incomingAxis, twinAxis);
      puzzle.visualize();
    }
    GeneralArchConsistency.printVariables(puzzle);
//    for (Variable var : puzzle.getVariables()) {
//      String incomingAxis = String.valueOf(var.getId().charAt(0));
//      String twinAxis = incomingAxis.equals("x") ? "y" : "x";
//      puzzle.pruneVariable(var, incomingAxis, twinAxis);
//      puzzle.visualize();
//    }
//    GeneralArchConsistency.printVariables(puzzle);

    visualize(puzzle);
  }

  private void visualize(NonoCspPuzzle puzzle) {
    int x = 0;
    int y;
    Variable<ChunkVals> next;
    Board<NonoTile> board = (Board<NonoTile>) getAdapter();
    while ((next = puzzle.getVariable("x" + x)) != null) {
      NonoDomain domain = (NonoDomain) next.getDomain();
      ChunkVals chunkVals = domain.getCertainValues();
      for (y = 0; y < chunkVals.values.size(); y++) {
        board.get(x, y).setColor(chunkVals.values.get(y));
      }
      x++;
    }
    board.notifyDataChanged();
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= NonoUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(NonoUtils.samples.get(i));
  }

  @Override
  public int getDomainSize() {
    // do nothing
    return -1;
  }

  @Override
  public void sampleSelected(int i) {
    super.sampleSelected(i);
    test(i);
  }

  @Override
  public List<Constraint> getConstraints() {
    return constraints;
  }


  public void setConstraints(List<Constraint> constraints) {
    this.constraints = constraints;
  }
}
