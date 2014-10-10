package algorithms.csp.canonical_utils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Patrick on 10.09.2014.
 */
public abstract class Constraint<T> implements Iterable<Variable<T>> {

  private static final String TAG = Constraint.class.getSimpleName();

  public abstract boolean contains(Variable x);

  @Override
  public abstract Iterator<Variable<T>> iterator();


  public abstract boolean isSatisfied(List<Variable<T>> variables, Variable<T> focalVariable);

  public abstract void clearHasNext();

  @Override
  public abstract String toString();

  public abstract List<Variable<T>> getVariables();


  public abstract boolean isSatisfied(VariableList variables);

  public abstract boolean hasNext();

  public abstract String getNextVariableId();

  public abstract void removeFocalvariableFromTodo(Variable<T> focalVariable);
}
