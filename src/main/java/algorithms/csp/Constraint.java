package algorithms.csp;

import java.util.Collection;
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
  private final Set<String> variableList;
  private final Function function;
  private Variable focalVariable;

  public Constraint(List<Variable> variables, String expression) {
    HashMap<String, Variable> variableMap = new HashMap<>();
    variableList = new LinkedHashSet<>();
    for (Variable var : variables) {
      if (expression.contains(var.getId())) {
        variableMap.put(var.getId(), var);
      }
    }

    function = new Function().setVariables(variableMap).setExpression(expression);
    clearHasNext();
  }


  public boolean contains(Variable x) {
    return function.contains(x);
  }

  @Override
  public Iterator<Variable> iterator() {
    return function.getVariables().values().iterator();
  }

  public Variable getFocalVariable() {
    return focalVariable;
  }

  public void setFocalVariable(Variable focalVariable) {
    variableList.remove(focalVariable.getId());
    this.focalVariable = focalVariable;
  }

  public boolean hasNext() {
    return variableList.size() > 0;
  }

  public Variable getNextVariable() {
    String next = variableList.iterator().next();
    variableList.remove(next);
    return function.getVariables().get(next);

  }

  public boolean isSatisfied() {
    boolean satisfied = function.call();
    if (satisfied) {
//      Log.v(TAG, satisfied + ": " + function);
    }
    return satisfied;
  }

  public void clearHasNext() {
    for (Variable var : function.getVariables().values()) {
      if (!var.equals(focalVariable)) {
        variableList.add(var.getId());
      }
    }
  }

  public Collection<Variable> getVariables() {
    return function.getVariables().values();
  }
}
