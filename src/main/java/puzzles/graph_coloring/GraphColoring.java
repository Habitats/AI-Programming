package puzzles.graph_coloring;

import ai.AIMain;
import ai.Log;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.a_star_csp.AStarCspNode;
import algorithms.csp.CspPuzzle;
import algorithms.csp.GeneralArchConsistency;
import puzzles.gac.Sudoku;
import puzzles.graph_coloring.gui.GraphColoringGui;
import puzzles.graph_coloring.gui.interfaces.CspButtonListener;

/**
 * Created by anon on 22.09.2014.
 */
public class GraphColoring implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();
  private GraphColoringGui gui;
  private GraphColoringPuzzle puzzle;
  public static int assumption_count;
  private AStar astar;
  private AIAdapter adapter;


  @Override
  public void run() {
    gui = new GraphColoringGui();
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

  private void sudokuGac() {
    CspPuzzle sudoku = new Sudoku();

    GeneralArchConsistency.domainFilter(sudoku);

    sudoku.visualize();
  }

  @Override
  public void resetClicked() {
    astar.terminate();
  }

  @Override
  public void loadClicked() {
    //    new ShortestPath();
    assumption_count = 0;
    GraphColoringPuzzle.K = Integer.parseInt(gui.getK());
    String input = gui.getInput();
    puzzle = getPuzzleFromInput(input);
    astarCsp();

//    sudokuGac();
//    graphcColoringGac();
  }

  private GraphColoringPuzzle getPuzzleFromInput(String input) {
    GraphColoringPuzzle puzzle = new GraphColoringPuzzle(this);
    puzzle.setGui(gui);

    AIAdapter<ColorNode> graph = GraphColoringUtils.generateGraph(input);
    setAdapter(graph);
    puzzle.setVariables(puzzle.generateVariables());
    GraphColoringConstraintManager.getManager().initialize(graph, puzzle.getVariables());

    return puzzle;
  }

  public GraphColoringPuzzle getSamplePuzzle(int i) {
    if (i >= GraphColoringUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(GraphColoringUtils.samples.get(i));
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
