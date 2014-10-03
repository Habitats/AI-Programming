package puzzles.graph_coloring;

import java.util.List;

import ai.models.graph.ColorNode;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoringPuzzle implements AStarCspPuzzle {


  private final GraphColoring graphColoring;
  public static int K = 6;
  private VariableList variables;

  public GraphColoringPuzzle(GraphColoring graphColoring) {
    this.graphColoring = graphColoring;
  }

  public VariableList generateVariables() {
    VariableList variables = new VariableList();
    for (ColorNode node : graphColoring.getAdapter().getItems()) {
      Variable var = new Variable(node.getId(), getInitialDomain());
      variables.put(var);
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
    return getMinimalDomain(getVariables());
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

  public void setVariables(VariableList variables) {
    this.variables = variables;
  }

  private Variable getMinimalDomain(VariableList variables) {
    int min = Integer.MAX_VALUE;
    Variable minVar = null;
    for (Variable var : variables) {
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

  // GraphColoringButtonListener ///////////////////////


  // CspPuzzle /////////////////////////////////////////////////////////////
  @Override
  public List<Constraint> getConstraints() {
    return GraphColoringConstraintManager.getManager().getConstraints();
  }

  @Override
  public VariableList getVariables() {
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
    for (Variable variable : variables) {
      variable.update();
    }
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
    for (Variable variable : getVariables()) {
      dupe.getVariables().put(variable.copy());
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
//    successor = getMinimalDomain(getMostConstrained());
    successor = getMinimalDomain();
    return successor;
  }


}
