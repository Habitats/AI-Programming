package algorithms.csp.canonical_utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Patrick on 10.09.2014.
 */
public class Constraint implements Iterable<Variable> {

  private static final String TAG = Constraint.class.getSimpleName();
  private final Set<String> variableIdsToCheck;
  private final Function function;
  private boolean satisfied;

  public Constraint(List<Variable> variables, String expression) {
    HashMap<String, Variable> variableMap = new HashMap<>();
    variableIdsToCheck = new LinkedHashSet<>();
    for (Variable var : variables) {
      if (expression.contains(var.getId())) {
        variableMap.put(var.getId(), var);
      }
    }

    function = new Function(variableMap, expression);
    clearHasNext();
  }

  public void removeFocalvariableFromTodo(Variable focalVariable) {
    for (String id : variableIdsToCheck) {
      if (focalVariable.getId().equals(id)) {
        variableIdsToCheck.remove(id);
        break;
      }
    }
  }

  public boolean contains(Variable x) {
    return function.contains(x);
  }

  @Override
  public Iterator<Variable> iterator() {
    return function.getVariablesMap().values().iterator();
  }

  public boolean hasNext() {
    return variableIdsToCheck.size() > 0;
  }

  public String getNextVariableId() {
    String next = variableIdsToCheck.iterator().next();
    variableIdsToCheck.remove(next);
    return function.getVariablesMap().get(next).getId();

  }

  public boolean isSatisfied(List<Variable> variables, Variable focalVariable) {
    boolean satisfied = function.call(variables, focalVariable);
//    if (satisfied) {
//    }
//    Log.v(TAG, satisfied + ": " + function);
    return satisfied;
  }

  public void clearHasNext() {
    for (Variable var : function.getVariablesMap().values()) {
      variableIdsToCheck.add(var.getId());
    }
  }

  @Override
  public String toString() {
    return function.toString();
  }

  public List<Variable> getVariables() {
    return new ArrayList<>(function.getVariablesMap().values());
  }


  public boolean isSatisfied(List<Variable> variables) {
   return function.call(variables);
  }
}
