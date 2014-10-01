package puzzles.flow;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import ai.models.graph.Graph;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import puzzles.graph_coloring.GraphColoringConstraintManager;
import puzzles.graph_coloring.GraphColoringUtils;
import puzzles.flow.gui.FlowGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Flow extends AStarCsp implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();

  @Override
  protected AIGui generateGui() {
    return new FlowGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new FlowCspPuzzle(this);
  }

  @Override
  protected void initializeAdapter(AStarCspPuzzle puzzle, AIAdapter<ColorNode> graph) {
    GraphColoringConstraintManager.getManager().initialize(graph, puzzle.getVariables());
  }

  @Override
  protected Graph<ColorNode> generateAdapter(String input) {
    return GraphColoringUtils.generateGraph(input);
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= FlowUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(FlowUtils.samples.get(i));
  }
}
