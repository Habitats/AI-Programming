package aiprog;

import a_star.Astar;
import a_star.gui.BoardGridBag;
import a_star.interfaces.AstarButtonListener;
import a_star.models.Board;
import a_star.models.Tile;
import a_star.utils.InputUtils;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Main implements AstarButtonListener {

  private static final String TAG = Main.class.getSimpleName();
  private final BoardGridBag gui;

  public static void main(String args[]) {
    new Main();
  }

  public Main() {
    gui = new BoardGridBag();
    gui.setListener(this);
//    initializeTestBoard();
    new Astar().run();
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
  public void runClicked() {
  }

  @Override
  public void resetClicked() {
    initializeBoard();
  }

  @Override
  public void loadClicked() {
    initializeBoard();
  }
}
