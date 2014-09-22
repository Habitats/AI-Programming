package puzzles.graph_coloring;

import java.util.List;

import ai.AIMain;
import ai.Log;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.a_star_csp.AStarCspNode;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspPuzzle;
import algorithms.csp.GeneralArchConsistency;
import algorithms.csp.canonical_utils.Constraint;
import puzzles.gac.Sudoku;
import puzzles.graph_coloring.gui.GraphColoringGui;
import puzzles.graph_coloring.gui.interfaces.CspButtonListener;

/**
 * Created by anon on 22.09.2014.
 */
public class GraphColoringMain implements CspButtonListener {

  public static final String TAG = AIMain.class.getSimpleName();
  private final GraphColoringGui gui;
  private GraphColoring puzzle;


  public GraphColoringMain() {
    gui = new GraphColoringGui();
    gui.setListener(this);

  }

  private void astarCsp() {

    AStarNode start = new AStarCspNode(puzzle);
    AStar astar = new AStar(start, new AStarCallback() {

      @Override
      public void finished(AStarNode best, AStar aStar) {
        Log.v(TAG, "success! " + best);
      }

      @Override
      public void error() {
        Log.v(TAG, "fail!");
      }
    });
    astar.runInBackground();
    Log.i(TAG, astar);
  }

  private void sudokuGac() {
    CspPuzzle sudoku = new Sudoku();

    GeneralArchConsistency.domainFilter(sudoku);

    sudoku.visualize();
  }

  public GraphColoring getSamplePuzzle(int i) {
    if (i >= GraphColoringUtils.samples.size()) {
      return null;
    }
    GraphColoring puzzle = new GraphColoring();
    puzzle.setGui(gui);

    AIAdapter<ColorNode> graph = GraphColoringUtils.generateGraph(GraphColoringUtils.samples.get(i));
    puzzle.setAdapter(graph);
    puzzle.setVariables(puzzle.generateVariables());
    GraphColoringConstraintManager.getManager().initialize(graph, puzzle.getVariables());

    return puzzle;
  }

  private void gacFilteringDupeTest(AStarCspPuzzle puzzle) {
    GeneralArchConsistency.Result res;
    puzzle.getVariables().get(0).setAssumption(0);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

    AStarCspPuzzle dupe = puzzle.duplicate();
    List<Constraint> c = dupe.getConstraints();
    dupe.getVariables().get(1).setAssumption(1);
    res = GeneralArchConsistency.domainFilter(dupe);
    Log.v(TAG, res.name());

    AStarCspPuzzle dupe2 = dupe.duplicate();
    dupe2.getVariables().get(2).setAssumption(2);
    res = GeneralArchConsistency.domainFilter(dupe2);
    Log.v(TAG, res.name());

    AStarCspPuzzle dupe3 = dupe2.duplicate();
    dupe3.getVariables().get(3).setAssumption(3);
    res = GeneralArchConsistency.domainFilter(dupe3);
    Log.v(TAG, res.name());
  }

  private void gacFilteringTest(AStarCspPuzzle puzzle) {
    GeneralArchConsistency.Result res;
    puzzle.getVariables().get(0).setAssumption(0);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

    List<Constraint> c = puzzle.getConstraints();
    puzzle.getVariables().get(1).setAssumption(1);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

    puzzle.getVariables().get(2).setAssumption(2);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

    puzzle.getVariables().get(3).setAssumption(3);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

  }


  @Override
  public void resetClicked() {

  }

  @Override
  public void loadClicked() {
    //    new ShortestPath();
    astarCsp();
//    sudokuGac();
//    graphcColoringGac();
  }

  @Override
  public void stepClicked() {

  }

  @Override
  public void stepChanged(int value) {
  }

  @Override
  public void sampleSelected(int i) {
    this.puzzle = getSamplePuzzle(i);
  }
}
