package shortestpath;

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

  public static void main(String args[]) {
    new ShortestPathController();
  }

  public ShortestPathController() {
    gui = new BoardGridBag();
    gui.setListener(this);
//    initializeTestBoard();
//    new Astar().run();
  }

  private void initializeTestBoard() {
    Board board = new Board(6, 6);
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
      Board board = InputUtils.build(gui.getInput()).getBoard();
      gui.setAdapter(board);
      Log.v(TAG, "Successfully loaded input!");
    } catch (Exception e) {
      Log.v(TAG, "Unable to parse input!");
    }
  }

  @Override
  public void astarClicked() {
    Log.v(TAG, "A* not implemented!");
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
