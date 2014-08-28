package puzzles.shortestpath;

import java.util.ArrayList;
import java.util.List;

import ai.Log;
import ai.models.Board;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import puzzles.shortestpath.gui.BoardGridBag;
import puzzles.shortestpath.interfaces.ShortestPathButtonListener;
import puzzles.shortestpath.utils.InputUtils;

/**
 * Created by Patrick on 24.08.2014.
 */
public class ShortestPath implements ShortestPathButtonListener {

  private static final String TAG = ShortestPath.class.getSimpleName();
  private BoardGridBag gui;
  private Board board;
  private AStar astar;
  private List<AStar> searches;

  public static void main(String args[]) {
    new ShortestPath();
  }

  public ShortestPath() {
    gui = new BoardGridBag();
    gui.setListener(ShortestPath.this);
    searches = new ArrayList<AStar>();
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
    startSearch(AStar.Traversal.BEST_FIRST);

  }

  private void startSearch(AStar.Traversal traversal) {
    AStarNode start = new BoardNode(board.getStart(), board);
    AStarNode goal = new BoardNode(board.getGoal(), board);
    astar = new AStar(start, goal, traversal, new AStarCallback() {
      @Override
      public void finished(AStarNode best) {
        best.visualize();
      }

      @Override
      public void error() {

      }
    });
    searches.add(astar);
    new Thread(astar).start();
  }

  @Override
  public void resetClicked() {
    for (AStar astar : searches) {
      astar.terminate();
    }
    initializeBoard();
  }

  @Override
  public void loadClicked() {
    initializeBoard();
  }

  @Override
  public void dfsClicked() {
    startSearch(AStar.Traversal.DEPTH_FIRST);
  }

  @Override
  public void bfsClicked() {
    startSearch(AStar.Traversal.BREATH_FIRST);
  }

  @Override
  public void stepClicked() {
    synchronized (astar) {
      for (AStar astar : searches) {
        astar.notify();
      }
    }
  }

  @Override
  public void stepChanged(int value) {
    for (AStar astar : searches) {
      astar.setStepTime(value);
    }
  }
}
