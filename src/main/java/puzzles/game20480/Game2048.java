package puzzles.game20480;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.AIMain;
import ai.Log;
import ai.gui.AICanvas;
import algorithms.minimax.MiniMax;
import algorithms.minimax.MiniMaxState;
import puzzles.game20480.gui.Game2048Gui;

import static ai.gui.AICanvas.Direction.DOWN;
import static ai.gui.AICanvas.Direction.LEFT;
import static ai.gui.AICanvas.Direction.RIGHT;
import static ai.gui.AICanvas.Direction.UP;

/**
 * Created by Patrick on 30.10.2014.
 */
public class Game2048 implements Runnable, Game2048ButtonListener {

  private static final String TAG = Game2048.class.getSimpleName();
  private Game2048Board board;
  private Game2048Gui gui;

  @Override
  public void run() {
    gui = new Game2048Gui(this);
    new Thread(gui).start();
    for (int i = 0; i < 10; i++) {
      initialize();
    }
  }

  private void initialize() {
    board = new Game2048Board();
    gui.setAdapter(board);

    board.place();

    while (true) {
//      List<MiniMaxState> tree = MiniMax.getSearchTree(board, 2);
//
//      MiniMaxState bestScore = MiniMax.getBestState(tree);

      Map<MiniMaxState, Integer> move = new HashMap<>();
      int max = Integer.MIN_VALUE;
      MiniMaxState best = null;
      Log.v(TAG, "current:");

      board.printBoard();

      for (MiniMaxState next : board.getPossibleNextStates()) {
        int value = MiniMax.alphaBeta(next, 6);
        ((Game2048Board) next).printBoard();
        Log.v(TAG, "score: " + value);
        if (value > max) {
          max = value;
          best = next;
        }
        move.put(next, value);
      }

      AICanvas.Direction bestMove = ((Game2048Board) best).getLastMove();
      Log.v(TAG, "moving " + bestMove.name());
      board.move(bestMove);
      board.notifyDataChanged();

      if (board.isTerminal()) {
        break;
      }

      stepAndWait();
    }

    Log.i(TAG, board.getMaxScore());
  }

  private synchronized void stepAndWait() {
    try {
//      Log.v(TAG, "waiting...");
      if (AIMain.MANUAL_STEP) {
        wait();
      } else {
        wait(20);
      }
//      Log.v(TAG, "continuing!");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private Game2048Board getNextBoard(Game2048Board board) {
    Queue<Game2048Board> boards = new PriorityQueue<>();
    for (AICanvas.Direction dir : AICanvas.Direction.values()) {
      Game2048Board next = board.copy();
      if (next.move(dir, false)) {
        boards.add(next);
      }
    }
    return boards.poll();
  }

  private void doSomeMoves(Game2048Board board) {
    Game2048Board bestNextBoard;
    while ((bestNextBoard = getNextBoard(board.copy())) != null) {
      AICanvas.Direction bestMove = bestNextBoard.getLastMove();
      board.move(bestMove);
    }
    Log.v(TAG, "board:  " + board.getScore());
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
    doSomeMoves(board);
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
