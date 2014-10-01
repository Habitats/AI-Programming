package puzzles.flow;

import java.util.Arrays;
import java.util.List;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Board;
import ai.models.grid.Tile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import puzzles.flow.gui.FlowGui;
import puzzles.shortestpath.BoardNode;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Flow extends AStarCsp<Tile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();

  @Override
  protected AIGui generateGui() {
    return new FlowGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new FlowCspPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<Tile> adapter) {
    //TODO: gen constraints
  }

  @Override
  protected AIAdapter<Tile> generateAdapter(String input) {
    List<String> inputList = Arrays.asList(input.split("\\n"));
    int dimension = Integer.parseInt(inputList.get(0).split("\\s+")[0]);
    int numberOfColors = Integer.parseInt(inputList.get(0).split("\\s+")[1]);

    Board board = new Board();
    board.initEmptyBoard(dimension, dimension);
    for (int i = 1; i < inputList.size(); i++) {
      String[] pairRow = inputList.get(i).split("\\s+");
      int startX = Integer.parseInt(pairRow[1]);
      int startY = Integer.parseInt(pairRow[2]);
      int endX = Integer.parseInt(pairRow[3]);
      int endY = Integer.parseInt(pairRow[4]);

      BoardNode start = new BoardNode(board.get(startX, startY), board);
      BoardNode end = new BoardNode(board.get(endX, endY), board);
    }
    return board;
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= FlowUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(FlowUtils.samples.get(i));
  }
}
