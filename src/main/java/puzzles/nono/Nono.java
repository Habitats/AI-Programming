package puzzles.nono;

import ai.AIMain;
import ai.Log;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.a_star_csp.AStarCspNode;
import algorithms.csp.CspButtonListener;
import puzzles.nono.gui.NonoGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Nono implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();
  private NonoGui gui;
  private NonoCspPuzzle puzzle;
  private AStar astar;
  private AIAdapter adapter;


  @Override
  public void run() {
    gui = new NonoGui();
    gui.setListener(this);
  }

  public void setAdapter(AIAdapter graph) {
    this.adapter = graph;
    gui.setAdapter(graph);
  }

  private void astarCsp() {

    AStarNode start = new AStarCspNode(puzzle);
    astar = new AStar(start, new AStarCallback() {

      @Override
      public void finished(AStarNode best, AStar aStar) {
        Log.i(TAG, "A* success! ");
        Log.i(TAG, best);
        Log.i(TAG, "Number of assumptions made: " + best.getPathLength());
      }

      @Override
      public void error() {
        Log.i(TAG, "A* failed to find a solution!");
      }
    });
    astar.runInBackground();
  }

  @Override
  public void resetClicked() {
    astar.terminate();
  }

  @Override
  public void loadClicked() {
    String input = gui.getInput();
    puzzle = getPuzzleFromInput(input);
    astarCsp();
  }

  private NonoCspPuzzle getPuzzleFromInput(String input) {
    NonoCspPuzzle puzzle = new NonoCspPuzzle(this);
    puzzle.setGui(gui);

    puzzle.setVariables(puzzle.generateVariables());

    return puzzle;
  }

  public NonoCspPuzzle getSamplePuzzle(int i) {
    if (i >= NonoUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(NonoUtils.samples.get(i));
  }

  @Override
  public void stepClicked() {
    synchronized (astar) {
      astar.notify();
    }
  }

  @Override
  public void stepChanged(int value) {
  }

  @Override
  public void sampleSelected(int i) {
//    this.puzzle = getSamplePuzzle(i);
  }

  public AIAdapter<ColorNode> getAdapter() {
    return adapter;
  }
}
