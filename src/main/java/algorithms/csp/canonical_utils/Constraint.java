package algorithms.csp.canonical_utils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Patrick on 10.09.2014.
 */
public abstract class Constraint<T> implements Iterable<Variable<Integer>> {

  private static final String TAG = Constraint.class.getSimpleName();
  private boolean satisfied;



  public abstract boolean contains(Variable x);

  @Override
  public abstract Iterator<Variable<Integer>> iterator();



  public abstract boolean isSatisfied(List<Variable<Integer>> variables, Variable<Integer> focalVariable);

  public abstract void clearHasNext();

  @Override
  public abstract String toString();

  public abstract List<Variable> getVariables();



  public abstract boolean isSatisfied(VariableList variables);

  public abstract boolean hasNext();

  public abstract String getNextVariableId();

  public abstract void removeFocalvariableFromTodo(Variable<T> focalVariable);
}
