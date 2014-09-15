package ai;

import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.a_star_csp.AStarCspNode;
import algorithms.csp.GeneralArchConsistency;
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
    astarCsp();
  }

  private static void astarCsp() {
    GraphColoring puzzle = new GraphColoring();

    GraphColoringGui gui = new GraphColoringGui();
    puzzle.setGui(gui);

    AIAdapter<ColorNode> graph = GraphInputUtils.generateGraph(GraphInputUtils.samples.get(1));
    puzzle.setAdapter(graph);

    GeneralArchConsistency gac = new GeneralArchConsistency(puzzle);
    AStarNode start = new AStarCspNode(gac);
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
}
