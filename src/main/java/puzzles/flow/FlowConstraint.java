package puzzles.flow;

import org.python.core.PyInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ai.Log;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by anon on 10.10.2014.
 */
public class FlowConstraint extends Constraint<Integer> {

  private static final String TAG = FlowConstraint.class.getSimpleName();
  private final Map<String, Variable<Integer>> variablesMap;
  private final String expression;

  public FlowConstraint(VariableList variables, String expression) {
    super(variables);
    variablesMap = new HashMap<>();
    this.expression = expression;

    for (Variable var : variables) {
      if (expression.contains(var.getId())) {
        variablesMap.put(var.getId(), var);
      }
    }
    clearHasNext();
  }

  public boolean isSatisfied(List<Variable<Integer>> variables, Variable<Integer> focalVariable) {
    Variable other;
    for (Variable var : variables) {
      if (var.getId().equals(focalVariable)) {
        continue;
      }
      other = var;

    }
//    return (baseValue != focalVariable.getValue())||
////    if (satisfied)k {
////    }
////    Log.v(TAG, satisfied + ": " + FlowFunction);
////    return !variables.get(0)
//    return satisfied;
    return false;
  }

  public Map<String, Variable<Integer>> getVariablesMap() {
    return variablesMap;
  }

  public boolean call(VariableList variables) {
    for (Variable var : variables) {
      if (variablesMap.containsKey(var.getId())) {
        variablesMap.put(var.getId(), var.copy());
      }
    }
    return call(variablesMap);
  }

  private boolean call(Map<String, Variable<Integer>> variablesMap) {
    // put the values in the right order according to how the parameters for the lambda was created
    PyInteger[] args = new PyInteger[variablesMap.size()];
    int i = 0;
    for (String key : variablesMap.keySet()) {
      args[i++] = new PyInteger(variablesMap.get(key).getValue());
    }

    // call the python lambda with args: x = 1, y = 2 etc, order is important
//    Log.v(TAG, toString());
    try {
      boolean ans = false;
      return ans;
    } catch (Exception e) {
      Log.v(TAG, "lambda crashed: " + toString(), e);
      throw new IllegalArgumentException();
    }
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

  @Deprecated
  public boolean isSatisfied(VariableList variables) {
    return call(variables);
  }


  public boolean contains(Variable x) {
    return contains(x);
  }

  @Override
  public Iterator<Variable<Integer>> iterator() {
    return getVariablesMap().values().iterator();
  }

  public void clearHasNext() {
    for (Variable var : getVariablesMap().values()) {
      variableIdsToCheck.add(var.getId());
    }
  }

  @Override
  public String toString() {
    return "yo";
  }

  public List<Variable<Integer>> getVariables() {
    return new ArrayList<>(getVariablesMap().values());
  }

}
