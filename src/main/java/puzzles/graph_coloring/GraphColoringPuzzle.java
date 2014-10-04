package puzzles.graph_coloring;

import java.util.List;

import ai.models.graph.ColorNode;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoringPuzzle extends SimpleAStarCspPuzzle {


  public static int K = 6;

  public GraphColoringPuzzle(AStarCsp<ColorNode> graphColoring) {
    super(graphColoring);
  }

  private VariableList getMostConstrained() {
    int max = Integer.MIN_VALUE;
    for (Variable var : getVariables()) {
      if (var.getDomain().getSize() == 1) {
        continue;
      }
      int constrainedCount = GraphColoringConstraintManager.getManager().getConstrainedCount(var);
      if (constrainedCount > max) {
        max = constrainedCount;
      }
    }
    VariableList mostContrained = new VariableList();
    for (Variable var : getVariables()) {
      if (GraphColoringConstraintManager.getManager().getConstrainedCount(var) == max) {
        mostContrained.put(var);
      }
    }
    return mostContrained;
  }

  // GraphColoringButtonListener ///////////////////////


  // CspPuzzle /////////////////////////////////////////////////////////////
  @Override
  public List<Constraint> getConstraints() {
    return GraphColoringConstraintManager.getManager().getConstraints();
  }


  @Override
  protected int getInitialDomainSize() {
    return GraphColoringPuzzle.K;
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new GraphColoringPuzzle(getAstarCsp());
  }
}
