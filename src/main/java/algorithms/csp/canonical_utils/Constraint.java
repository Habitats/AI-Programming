package algorithms.csp.canonical_utils;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algorithms.csp.CspPuzzle;

/**
 * Created by Patrick on 10.09.2014.
 */
public abstract class Constraint<T> implements Iterable<Variable<T>> {

  protected final Set<String> variableIdsToCheck;

  private static final String TAG = Constraint.class.getSimpleName();

  public Constraint(VariableList variables) {
    variableIdsToCheck = new LinkedHashSet<>();
  }

  public abstract boolean contains(Variable x);

  public abstract void addVariablesInConstraintsContainingCurrentVariable(CspPuzzle puzzle, Queue<Variable> queue,
                                                                          Set<String> queueHash, Variable var,
                                                                          Constraint constraint);

  @Override
  public abstract Iterator<Variable<T>> iterator();


  public abstract void clearHasNext();

  @Override
  public abstract String toString();

  public abstract List<Variable<T>> getVariables();


  public abstract boolean isSatisfied(VariableList variables);

  public abstract boolean hasNext();

  public abstract String getNextVariableId();

  public abstract void removeFocalvariableFromTodo(Variable<T> focalVariable);

  public abstract boolean revise(Variable focalVariable, CspPuzzle puzzle);

}
