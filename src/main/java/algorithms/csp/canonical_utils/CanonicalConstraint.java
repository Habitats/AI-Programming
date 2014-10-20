package algorithms.csp.canonical_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algorithms.csp.CspPuzzle;

/**
 * Created by anon on 10.10.2014.
 */
public class CanonicalConstraint extends Constraint<Integer> {

  private final Function function;

  public CanonicalConstraint(VariableList variables, String expression) {
    super(variables);
    HashMap<String, Variable<Integer>> variableMap = new HashMap<>();

    for (Variable var : variables) {
      if (expression.contains(var.getId())) {
        variableMap.put(var.getId(), var);
      }
    }

    function = new Function(variableMap, expression);
    clearHasNext();
  }

  public boolean isSatisfied(List<Variable<Integer>> variables, Variable<Integer> focalVariable) {
    boolean satisfied = function.call(variables, focalVariable);
//    if (satisfied) {
//    }
//    Log.v(TAG, satisfied + ": " + function);
    return satisfied;
  }

  public boolean hasNext() {
    return variableIdsToCheck.size() > 0;
  }

  public String getNextVariableId() {
    String next = variableIdsToCheck.iterator().next();
    variableIdsToCheck.remove(next);
    return next;

  }


  public void removeFocalvariableFromTodo(Variable focalVariable) {
    variableIdsToCheck.remove(focalVariable.getId());
  }

  public boolean isSatisfied(VariableList variables) {
    return function.call(variables);
  }

  public boolean contains(Variable x) {
    return function.contains(x);
  }

  @Override
  public void addVariablesInConstraintsContainingCurrentVariable(CspPuzzle puzzle, Queue<Variable> queue,
                                                                 Set<String> queueHash, Variable var,
                                                                 Constraint constraint) {
    List<Constraint<Integer>> constraintsContainingVariable = var.getConstraintsContainingVariable();
    for (Constraint<Integer> constraintContainingVariable : constraintsContainingVariable) {
      if (constraintContainingVariable.equals(constraint)) {
        continue;
      }
      for (Variable variableInConstraint : constraintContainingVariable) {
        if (variableInConstraint.getId().equals(var.getId())) {
          continue;
        }
        if (!queueHash.contains(variableInConstraint.getId())) {
          queue.add(puzzle.getVariable(variableInConstraint.getId()));
          queueHash.add(variableInConstraint.getId());
        }
      }
    }
  }

  @Override
  public Iterator<Variable<Integer>> iterator() {
    return function.getVariablesMap().values().iterator();
  }

  public void clearHasNext() {
    for (Variable var : function.getVariablesMap().values()) {
      variableIdsToCheck.add(var.getId());
    }
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof CanonicalConstraint && ((CanonicalConstraint) obj).function.equals(function);
  }

  @Override
  public String toString() {
    return function.toString();
  }

  public List<Variable<Integer>> getVariables() {
    return new ArrayList<>(function.getVariablesMap().values());
  }
}
