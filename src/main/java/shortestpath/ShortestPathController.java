package shortestpath;

import a_star.algorithm.Astar;
import a_star.node.Node;
import aiprog.Log;
import shortestpath.gui.BoardGridBag;
import shortestpath.interfaces.AstarButtonListener;
import shortestpath.models.Board;
import shortestpath.models.Tile;
import shortestpath.utils.InputUtils;

/**
 * Created by Patrick on 24.08.2014.
 */
public class ShortestPathController implements AstarButtonListener {

  private static final String TAG = ShortestPathController.class.getSimpleName();
  private final BoardGridBag gui;
  private Board board;

  public static void main(String args[]) {
    new ShortestPathController();
  }

  public ShortestPathController() {
    gui = new BoardGridBag();
    gui.setListener(this);
    initializeTestBoard2();
  }

  private void initializeTestBoard2() {
    board = InputUtils.build("(6,6)(1, 0)(5, 5)(3, 2, 2, 2)(0, 3, 1, 3)(2, 0, 4, 2)(2, 5, 2, 1)").getBoard();
    gui.setAdapter(board);
  }

  private void initializeTestBoard() {
    board = new Board(6, 6);
    gui.setAdapter(board);

    board.clear();
    board.set(0, 3, 1, 3, Tile.State.OBSTICLE);
    board.set(3, 2, 2, 2, Tile.State.OBSTICLE);
    board.set(2, 0, 4, 2, Tile.State.OBSTICLE);
    board.set(2, 5, 2, 1, Tile.State.OBSTICLE);
    board.set(1, 0, 1, 2, Tile.State.OUTLINE);
  }

  private void initializeBoard() {
    try {
      board = InputUtils.build(gui.getInput()).getBoard();
      gui.setAdapter(board);
      Log.v(TAG, board);
      Log.v(TAG, "Successfully loaded input!");
    } catch (Exception e) {
      Log.v(TAG, "Unable to parse input!");
    }
  }

  @Override
  public void astarClicked() {
    Astar astar = new Astar();
    Node start = new BoardNode(board.getStart(), board);
    Node goal = new BoardNode(board.getGoal(), board);
    Node best = astar.search(start, goal);
    if (best != null) {
      best.visualize();
    }
  }

  @Override
  public void resetClicked() {
    initializeBoard();
  }

  @Override
  public void loadClicked() {
    initializeBoard();
  }

  @Override
  public void dfsClicked() {
    Log.v(TAG, "DFS not implemented!");
  }

  @Override
  public void bfsClicked() {
    Log.v(TAG, "BFS not implemented!");
  }
}
