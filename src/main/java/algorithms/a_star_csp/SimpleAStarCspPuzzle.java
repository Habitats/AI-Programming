package algorithms.a_star_csp;

import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;
import puzzles.graph_coloring.GraphColoringPuzzle;

/**
 * Created by Patrick on 04.10.2014.
 */
public abstract class SimpleAStarCspPuzzle implements AStarCspPuzzle{

  protected VariableList variables;



  protected Domain getInitialDomain() {
    int[] domain = new int[getInitialDomainSize()];
    for (int i = 0; i < getInitialDomainSize(); i++) {
      domain[i] = i;
    }
    return new Domain(domain);
  }

  private int getInitialDomainSize() {
    return GraphColoringPuzzle.K;
  }

  protected Variable getMinimalDomain() {
    return getMinimalDomain(getVariables());
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

  @Override
  public VariableList getVariables() {
    return variables;
  }

  @Override
  public int getDomainSize() {
    int size = 0;
    for (Variable variable : variables) {
      size += variable.getDomain().getSize();
    }
    return size;
  }

  @Override
  public Variable getVariable(String id) {
    return variables.getVariable(id);
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
  public void setVariables(VariableList variables) {
    this.variables = variables;
  }

  @Override
  public Variable getSuccessor() {
    Variable successor;
//    successor = getMinimalDomain(getMostConstrained());
    successor = getMinimalDomain();
    return successor;
  }

  @Override
  public AStarCspPuzzle duplicate() {
    AStarCspPuzzle dupe = newInstance();
    dupe.setVariables(getVariables().copy());
    return dupe;
  }

  protected abstract AStarCspPuzzle newInstance();
}
