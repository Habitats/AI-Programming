package algorithms.csp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ai.Log;

/**
 * Created by Patrick on 10.09.2014.
 */
public class Constraint implements Iterable<Variable> {

  private static final String TAG = Constraint.class.getSimpleName();
  private final HashMap<String, Variable> variables;
  private final Set<String> variableList;
  private final Function function;
  private Variable focalVariable;
  private int index;

  public Constraint(List<Variable> variables, String expression) {
    this.variables = new HashMap<String, Variable>();
    variableList = new LinkedHashSet<>();
    for (Variable var : variables) {
      this.variables.put(var.getId(), var);
    }
    clearHasNext();

    function = new Function().setVariables(this.variables).setExpression(expression);
  }

  public boolean contains(Variable x) {
    return variables.containsKey(x.getId());
  }

  @Override
  public Iterator<Variable> iterator() {
    return variables.values().iterator();
  }

  public Collection<Variable> getVariables() {
    return variables.values();
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
    return variables.get(next);

  }

  public boolean isSatisfied() {
    boolean satisfied = function.call(this.variables);
    Log.v(TAG, satisfied + ": " + function);
    return satisfied;
  }

  public boolean isFocal(Variable variable) {
    return variable.equals(focalVariable);
  }

  public void clearHasNext() {
    for (Variable var : getVariables()) {
      if (!var.equals(focalVariable)) {
        variableList.add(var.getId());
      }
    }
  }
}
