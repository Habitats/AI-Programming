package puzzles.graph_coloring;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import puzzles.graph_coloring.gui.GraphColoringGui;

/**
 * Created by anon on 22.09.2014.
 */
public class GraphColoring extends AStarCsp<ColorNode> {

  public static final String TAG = AIMain.class.getSimpleName();

  @Override
  protected AIGui generateGui() {
    return new GraphColoringGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new GraphColoringPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<ColorNode> graph) {
    GraphColoringConstraintManager.getManager().generateConstraints(graph, puzzle.getVariables());

  }

  @Override
  protected AIAdapter<ColorNode> generateAdapter(String input) {
    return GraphColoringUtils.generateGraph(input);
  }

  @Override
  public void loadClicked() {
    GraphColoringPuzzle.K = Integer.parseInt(((GraphColoringGui) getGui()).getK());
    super.loadClicked();
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= GraphColoringUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(GraphColoringUtils.samples.get(i));
  }
}
