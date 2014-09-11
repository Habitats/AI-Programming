package algorithms.csp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.Log;

/**
 * Created by Patrick on 10.09.2014.
 */
public class Constraint implements Iterable<Variable> {

  private static final String TAG = Constraint.class.getSimpleName();
  private final HashMap<String, Variable> variables;
  private final Queue<String> variableList;
  private final Function function;
  private Variable focalVariable;
  private int index;

  public Constraint(List<Variable> variables, String expression) {
    this.variables = new HashMap<String, Variable>();
    variableList = new PriorityQueue<>();
    for (Variable var : variables) {
      this.variables.put(var.getId(), var);
      variableList.add(var.getId());
    }

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
    variableList.remove(focalVariable.getId());
    return focalVariable;
  }

  public void setFocalVariable(Variable focalVariable) {
    this.focalVariable = focalVariable;
  }

  public boolean hasNext() {
    return variableList.peek() != null;
  }

  public Variable getNextVariable() {
    return variables.get(variableList.poll());
  }

  public boolean isSatisfied() {
    boolean satisfied = function.call(this.variables);
      Log.v(TAG, satisfied + ": " + function);
    return satisfied;
  }
}
