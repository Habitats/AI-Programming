package puzzles.game20480;

import ai.Log;
import puzzles.game20480.gui.Game2048Gui;

import static ai.gui.AICanvas.Direction.DOWN;
import static ai.gui.AICanvas.Direction.LEFT;
import static ai.gui.AICanvas.Direction.RIGHT;
import static ai.gui.AICanvas.Direction.UP;

/**
 * Created by Patrick on 30.10.2014.
 */
public class Game2048 implements Runnable, GameButtonListener {

  private static final String TAG = Game2048.class.getSimpleName();
  private Game2048Board board;
  private Game2048Gui gui;

  @Override
  public void run() {
    gui = new Game2048Gui(this);
    initialize();
  }

  private void initialize() {
    board = new Game2048Board();
    gui.setAdapter(board);

    board.place();
  }

  @Override
  public void stepClicked() {
    Log.v(TAG, "step clicked");
  }

  @Override
  public void stepChanged(int value) {
    Log.v(TAG, "step changed: " + value);
  }

  @Override
  public void resetClicked() {
    initialize();
  }

  @Override
  public void runClicked() {
    Log.v(TAG, "run initiated ...");
    board.place();
  }

  @Override
  public void rightClicked() {
    Log.v(TAG, "moving right ...");
    board.move(RIGHT);
  }

  @Override
  public void downClicked() {
    Log.v(TAG, "moving down ...");
    board.move(DOWN);
  }

  @Override
  public void leftClicked() {
    Log.v(TAG, "moving left ...");
    board.move(LEFT);
  }

  @Override
  public void upClicked() {
    Log.v(TAG, "moving up ...");
    board.move(UP);
  }
}
