package algorithms.a_star_csp;

import java.util.ArrayList;
import java.util.List;

import ai.models.Node;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 04.10.2014.
 */
public abstract class SimpleAStarCspPuzzle<T extends Node<T> & VariableListener> implements AStarCspPuzzle {

  private final AStarCsp<T> astarCsp;
  protected VariableList variables;

  public SimpleAStarCspPuzzle(AStarCsp<T> astarCsp) {
    this.astarCsp = astarCsp;
  }


  protected Domain getInitialDomain() {
    Integer[] domain = new Integer[getInitialDomainSize()];
    for (int i = 0; i < getInitialDomainSize(); i++) {
      domain[i] = i;
    }
    return new Domain<>(domain);
  }

  @Override
  public void setAssumption(String variableId, Object value) {
    Variable variable = getVariable(variableId);
    variable.setAssumption(value);
  }

  protected int getInitialDomainSize() {
    return getAstarCsp().getDomainSize();
  }

  protected Variable getMinimalDomain() {
    return getMinimalDomain(getVariables());
  }

  protected Variable getMinimalDomain(VariableList variables) {
    int min = Integer.MAX_VALUE;
    Variable minVar = null;
    List<Variable> vars = variables.getAll();
//    Collections.shuffle(vars);
    for (Variable var : vars) {
      if (var.getDomain().getSize() == 1) {
        continue;
      }
      if (var.getDomain().getSize() < min) {
        min = var.getDomain().getSize();
        minVar = var;
      }
    }
    List<Variable> minVars = new ArrayList<>();
    for (Variable var : vars) {
      if (var.getDomain().getSize() == min) {
        minVars.add(minVar);
      }
    }
    int index = (int) (minVars.size() * Math.random());
    return minVars.get(index);
  }

  /**
   * Solution should return 0, and the worst possible state should return a high number
   * @return
   */
  @Override
  public int getHeuristic() {
    return getDomainSize() - getVariables().size();
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
      sb.append(var.getId()).append(":").append(var.getDomain().getId()).append(" ");
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

  protected AStarCsp<T> getAstarCsp() {
    return astarCsp;
  }

  @Override
  public List<Constraint> getConstraints() {
    return getAstarCsp().getConstraints();
  }

  @Override
  public void visualize() {
    for (Variable variable : getVariables()) {
      variable.update();
    }
    getAstarCsp().getAdapter().notifyDataChanged();
  }

  @Override
  public abstract VariableList generateVariables();
}
