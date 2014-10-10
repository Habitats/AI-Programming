package algorithms.csp.canonical_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by anon on 10.10.2014.
 */
public class CanonicalConstraint extends Constraint<Integer> {

  private final Function function;
  private final Set<String> variableIdsToCheck;

  public CanonicalConstraint(VariableList variables, String expression) {
    super();
    HashMap<String, Variable<Integer>> variableMap = new HashMap<>();
    variableIdsToCheck = new LinkedHashSet<>();
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

  public List<Variable> getVariables() {
    return new ArrayList<>(function.getVariablesMap().values());
  }
}
