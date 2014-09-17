package ai;

import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.a_star_csp.AStarCspNode;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspPuzzle;
import algorithms.csp.GeneralArchConsistency;
import puzzles.gac.Sudoku;
import puzzles.graph_coloring.ConstraintManager;
import puzzles.graph_coloring.GraphColoring;
import puzzles.graph_coloring.GraphInputUtils;
import puzzles.graph_coloring.gui.GraphColoringGui;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AIMain {

  public static final String TAG = AIMain.class.getSimpleName();

  public static void main(String[] args) {
//    new ShortestPath();
//    astarCsp();
//    sudokuGac();
    graphcColoringGac();
  }

  private static void astarCsp() {

    AStarNode start = new AStarCspNode(getGraphColoringInstance());
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
    astar.run();
  }

  private static void sudokuGac() {
    CspPuzzle sudoku = new Sudoku();

    GeneralArchConsistency.domainFilter(sudoku);

    sudoku.visualize();
  }

  private static void graphcColoringGac() {
    AStarCspPuzzle puzzle = getGraphColoringInstance();
    GeneralArchConsistency.Result res;

    puzzle.getVariables().get(0).setAssumption(0);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

    AStarCspPuzzle dupe = puzzle.duplicate();
    dupe.getVariables().get(1).setAssumption(1);
    res = GeneralArchConsistency.domainFilter(dupe);
    Log.v(TAG, res.name());

    puzzle.getVariables().get(1).setAssumption(1);
    res = GeneralArchConsistency.domainFilter(puzzle);
    Log.v(TAG, res.name());

//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(2).setAssumption(2);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());
//
//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(3).setAssumption(3);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());
//
//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(4).setAssumption(2);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());
//
//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(5).setAssumption(1);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());
//
//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(6).setAssumption(0);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());
//
//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(7).setAssumption(3);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());
//
//    puzzle = puzzle.duplicate();
//    puzzle.getVariables().get(8).setAssumption(2);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());

//    puzzle.getVariables().get(30).setAssumption(2);
//    res = GeneralArchConsistency.domainFilter(puzzle);
//    Log.v(TAG, res.name());

  }

  private static GraphColoring getGraphColoringInstance() {
    GraphColoring puzzle = new GraphColoring();

    GraphColoringGui gui = new GraphColoringGui();
    puzzle.setGui(gui);

    AIAdapter<ColorNode> graph = GraphInputUtils.generateGraph(GraphInputUtils.samples.get(1));
    puzzle.setAdapter(graph);
    puzzle.setVariables(puzzle.generateVariables());
    ConstraintManager.getManager().initialize(graph, puzzle.getVariables());

    return puzzle;
  }
}
