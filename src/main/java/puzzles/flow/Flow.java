package puzzles.flow;

import ai.AIMain;
import ai.Log;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.a_star_csp.AStarCspNode;
import puzzles.flow.gui.FlowGui;
import puzzles.graph_coloring.GraphColoringConstraintManager;
import puzzles.graph_coloring.GraphColoringUtils;
import algorithms.csp.CspButtonListener;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Flow implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();
  private FlowGui gui;
  private FlowCspPuzzle puzzle;
  private AStar astar;
  private AIAdapter adapter;


  @Override
  public void run() {
    gui = new FlowGui();
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

  private FlowCspPuzzle getPuzzleFromInput(String input) {
    FlowCspPuzzle puzzle = new FlowCspPuzzle(this);
    puzzle.setGui(gui);

    AIAdapter<ColorNode> graph = GraphColoringUtils.generateGraph(input);
    setAdapter(graph);
    puzzle.setVariables(puzzle.generateVariables());
    GraphColoringConstraintManager.getManager().initialize(graph, puzzle.getVariables());

    return puzzle;
  }

  public FlowCspPuzzle getSamplePuzzle(int i) {
    if (i >= FlowUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(FlowUtils.samples.get(i));
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
