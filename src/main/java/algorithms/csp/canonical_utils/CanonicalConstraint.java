package algorithms.csp.canonical_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.CspPuzzle;

/**
 * Created by anon on 10.10.2014.
 */
public class CanonicalConstraint extends Constraint<Integer> {

  private static final String TAG = CanonicalConstraint.class.getSimpleName();
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


  /**
   * Remove all values from focalVariables' domain if no combination of non-focalVariables satisfy the constraint
   *
   * @return return true if domain is reduced by assumption
   */

  public boolean revise(Variable focalVariable, CspPuzzle puzzle) {
    int oldSize = focalVariable.getDomain().getSize();
    removeInvalidValues(focalVariable, puzzle);

    int newSize = focalVariable.getDomain().getSize();
//    Log.v(TAG, "old: " + oldSize + " new: " + newSize);
    return oldSize > newSize;
  }

  public boolean isSatisfied(List<Variable> variables, Variable<Integer> focalVariable) {
    long start = System.nanoTime();
    boolean satisfied = function.call(variables, focalVariable);

   SimpleAStarCspPuzzle.avg +=  System.nanoTime() - start;
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

  /**
   * Check all permutations of variables and values and isSatisfiable if the expression is isSatisfiable.
   *
   * @return true on the first isSatisfiable occurrence, false is no combination satisfies expression
   */
  private boolean isSatisfiable(CanonicalConstraint constraint, int focalVariableIndex, List<Variable> vars,
                                Variable focalVariable, CspPuzzle puzzle) {
    boolean hasMoreVariables = constraint.hasNext() || vars.size() > focalVariableIndex;
    if (hasMoreVariables) {
      // isSatisfiable if it's the first time we see this variable. If yes, put it to current variables
      if (vars.size() == focalVariableIndex) {
        String id = constraint.getNextVariableId();
        vars.add(puzzle.getVariable(id));
      }
      // iterate over all possible values for this variable
      for (Object nextValue : vars.get(focalVariableIndex).getDomain()) {
        // put a value, and recursively combine it with the possible combinations of the remaining variables
        vars.get(focalVariableIndex).setValue(nextValue);
        if (isSatisfiable(constraint, focalVariableIndex + 1, vars, focalVariable, puzzle)) {
          return true;
        }
      }
    } else {
      // return on the first isSatisfiable occurrence
      if (constraint.isSatisfied(vars, focalVariable)) {
        return true;
      }
    }
    return false;
  }

  private void removeInvalidValues(Variable focalVariable, CspPuzzle puzzle) {
    // iterate over all the values of the focalDomain
    Iterator<Integer> iterator = focalVariable.getDomain().iterator();
    CanonicalConstraint constraint = this;
    while (iterator.hasNext()) {
      Object val = iterator.next();
      focalVariable.setValue(val);

      List<Variable> vars = new ArrayList<>();
      constraint.clearHasNext();
      constraint.removeFocalvariableFromTodo(focalVariable);
      boolean satisfiable = isSatisfiable(constraint, 0, vars, focalVariable, puzzle);
      if (!satisfiable) {
        // if constraint is impossible to satisfy with the given value, remove the value from the domain
//        Log.v(TAG, "reducing the domain of " + focalVariable + " by removing: " + val + ". Violating: " + constraint);
        iterator.remove();
      }
//      else{
//        Log.v(TAG, "unable to reduce the domain of " + focalVariable + " with values:  " + val + " and constraint: " + constraint);
//      }
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
