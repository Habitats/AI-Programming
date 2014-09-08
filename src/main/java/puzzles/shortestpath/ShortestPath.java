package puzzles.shortestpath;

import java.util.ArrayList;
import java.util.List;

import ai.AIMain;
import ai.Log;
import ai.models.Board;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import puzzles.shortestpath.gui.ShortestPathGui;
import puzzles.shortestpath.interfaces.ShortestPathButtonListener;
import puzzles.shortestpath.utils.InputUtils;

/**
 * Created by Patrick on 04.09.2014.
 */
public class ShortestPath implements ShortestPathButtonListener {

  private static final String TAG = AIMain.class.getSimpleName();
  private ShortestPathGui gui;
  private Board board;
  private AStar astar;
  private List<AStar> searches;

  public ShortestPath() {
    // initialize the GUI
    gui = new ShortestPathGui();
    gui.setListener(ShortestPath.this);

    // since it's possible to run multiple searches in parallel, let's store each instance in a list to keep track of them
    searches = new ArrayList<AStar>();
  }

  /**
   * Initialize the GUI based on the sample inputs found in InputUtils
   */
  private void initializeBoard() {
    try {
      board = InputUtils.build(gui.getInput()).getBoard();
      gui.setAdapter(board);
      Log.v(TAG, board);
      Log.v(TAG, "Successfully loaded input!");
    } catch (Exception e) {
      Log.v(TAG, "Unable to parse input!", e);
    }
  }

  @Override
  public void astarClicked() {
    startSearch(AStar.Traversal.BEST_FIRST);
  }

  private void startSearch(AStar.Traversal traversal) {
    AStarCallback callback = new AStarCallback() {
      @Override
      public void finished(AStarNode best, AStar aStar) {
        // the visualize method is a general method for visualizing the path
        best.visualize();
      }

      @Override
      public void error() {

      }
    };
    startSearch(traversal, callback);
  }

  /**
   * Initiate a new search
   *
   * @param traversal - Enum constant to decide the traversal method (BEST_FIRST, DEPTH_FIRST or BREATH_FIRST)
   * @param callback  - Since a search can run asynch, a callback is required
   */
  private void startSearch(AStar.Traversal traversal, AStarCallback callback) {
    AStarNode start = new BoardNode(board.getStart(), board);
    AStarNode goal = new BoardNode(board.getGoal(), board);
    astar = new AStar(start, goal, traversal, callback);
    searches.add(astar);
    new Thread(astar).start();
  }

  private void runSimulation() {
    startSearch(AStar.Traversal.BEST_FIRST);
    startSearch(AStar.Traversal.DEPTH_FIRST);
    startSearch(AStar.Traversal.BREATH_FIRST);
  }

  // GUI LISTENER METHODS BELOW

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

  @Override
  public void simulationClicked() {
    runSimulation();
  }


}
