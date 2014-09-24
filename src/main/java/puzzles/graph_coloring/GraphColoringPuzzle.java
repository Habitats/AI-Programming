package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.List;

import ai.models.graph.ColorNode;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import puzzles.graph_coloring.gui.GraphColoringGui;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoringPuzzle implements AStarCspPuzzle {


  private final GraphColoring graphColoring;
  private GraphColoringGui gui;
  public static int K = 6;
  private List<Variable> variables;

  public GraphColoringPuzzle(GraphColoring graphColoring) {
    this.graphColoring = graphColoring;
  }


  public void setGui(GraphColoringGui gui) {
    this.gui = gui;
  }

  public List<Variable> generateVariables() {
    List<Variable> variables = new ArrayList<>();
    for (ColorNode node : graphColoring.getAdapter().getItems()) {
      Variable var = new Variable(node.getId(), getInitialDomain());
      variables.add(var);
      var.setListener(node);
    }
    return variables;
  }

  private Domain getInitialDomain() {
    int[] domain = new int[K];
    for (int i = 0; i < K; i++) {
      domain[i] = i;
    }
    return new Domain(domain);
  }

  private Variable getMinimalDomain() {
    int min = Integer.MAX_VALUE;
    Variable minVar = null;
    for (Variable var : getVariables()) {
      if (var.getDomain().getSize() == 1) {
        continue;
      }
      if (var.getDomain().getSize() < min) {
        min = var.getDomain().getSize();
        minVar = var;
      }
    }
    return minVar;
  }

  private Variable getMostConstrained() {
    int max = Integer.MIN_VALUE;
    Variable maxConstrained = null;
    for (Variable var : getVariables()) {
      if (var.getDomain().getSize() == 1) {
        continue;
      }
      int constrainedCount = GraphColoringConstraintManager.getManager().getConstrainedCount(var);
      if (constrainedCount > max) {
        max = constrainedCount;
        maxConstrained = var;
      }
    }
    return maxConstrained;
  }

  public void setVariables(List<Variable> variables) {
    this.variables = variables;
  }

  // GraphColoringButtonListener ///////////////////////


  // CspPuzzle /////////////////////////////////////////////////////////////
  @Override
  public List<Constraint> getConstraints() {
    return GraphColoringConstraintManager.getManager().getConstraints();
  }

  @Override
  public List<Variable> getVariables() {
    return variables;
  }

  @Override
  public int getDomainSize() {
    int size = 0;
    for (Variable variable : getVariables()) {
      size += variable.getDomain().getSize();
    }
    return size;
  }

  @Override
  public void visualize() {
    graphColoring.getAdapter().notifyDataChanged();
  }

  @Override
  public Variable getVariable(String id) {
    for (Variable var : getVariables()) {
      if (var.getId().equals(id)) {
        return var;
      }
    }
    return null;
  }

  // AStarCspPuzzle /////////////////////////////////////////////////////////////////

  @Override
  public AStarCspPuzzle duplicate() {
    GraphColoringPuzzle dupe = new GraphColoringPuzzle(graphColoring);
    dupe.setVariables(dupe.generateVariables());
    for (int i = 0; i < getVariables().size(); i++) {
      dupe.getVariables().set(i, getVariables().get(i).copy());
    }
    return dupe;
  }

  @Override
  public String getId() {
    StringBuilder sb = new StringBuilder();
    for (Variable var : getVariables()) {
      sb.append(var.getId() + ":" + var.getDomain().getId() + " ");
    }
    return sb.toString();
  }

  @Override
  public Variable getSuccessor() {
    Variable successor;
//    successor = getMostConstrained();
    successor = getMinimalDomain();
    return successor;
  }


}
