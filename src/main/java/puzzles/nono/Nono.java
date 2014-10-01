package puzzles.nono;

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
import puzzles.nono.gui.NonoGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Nono extends AStarCsp implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();

  @Override
  protected AIGui generateGui() {
    return new NonoGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new NonoCspPuzzle(this);
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
    if (i >= NonoUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(NonoUtils.samples.get(i));
  }
}
