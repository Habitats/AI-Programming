package puzzles.shortestpath;

import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import puzzles.shortestpath.gui.BoardGridBag;
import puzzles.shortestpath.interfaces.ShortestPathButtonListener;
import ai.models.Board;
import puzzles.shortestpath.utils.InputUtils;
import ai.Log;

/**
 * Created by Patrick on 24.08.2014.
 */
public class ShortestPathController implements ShortestPathButtonListener {

  private static final String TAG = ShortestPathController.class.getSimpleName();
  private BoardGridBag gui;
  private Board board;
  private AStar astar;

  public static void main(String args[]) {
    new ShortestPathController();
  }

  public ShortestPathController() {
    gui = new BoardGridBag();
    gui.setListener(ShortestPathController.this);
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
    AStarNode start = new BoardNode(board.getStart(), board);
    AStarNode goal = new BoardNode(board.getGoal(), board);
    astar = new AStar(start, goal, new AStarCallback() {
      @Override
      public void finished(AStarNode best) {
        best.visualize();
      }

      @Override
      public void error() {

      }
    });

    new Thread(astar).start();
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

  @Override
  public void stepClicked() {
    synchronized (astar) {
      astar.notify();
    }
  }
}