package puzzles.nono.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;
import puzzles.nono.ChunkVals;

/**
 * Created by Patrick on 20.10.2014.
 */
public class NonoConstraint extends Constraint<ChunkVals> {

  public NonoConstraint(VariableList variables) {
    super(variables);
  }

  @Override
  public boolean contains(Variable x) {
    return false;
  }

  @Override
  public void addVariablesInConstraintsContainingCurrentVariable(CspPuzzle puzzle, Queue<Variable> queue,
                                                                 Set<String> queueHash, Variable var,
                                                                 Constraint constraint) {
  }

  @Override
  public Iterator<Variable<ChunkVals>> iterator() {
    return null;
  }

  @Override
  public boolean isSatisfied(List<Variable<ChunkVals>> variables, Variable<ChunkVals> focalVariable) {
    return false;
  }

  @Override
  public void clearHasNext() {

  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public List<Variable<ChunkVals>> getVariables() {
    return null;
  }

  @Override
  public boolean isSatisfied(VariableList variables) {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public String getNextVariableId() {
    return null;
  }

  @Override
  public void removeFocalvariableFromTodo(Variable<ChunkVals> focalVariable) {

  }
}
