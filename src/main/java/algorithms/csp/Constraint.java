package algorithms.csp;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Patrick on 10.09.2014.
 */
public class Constraint implements Iterable<Constraint> {

  public boolean contains(Variable x) {
    return false;
  }

  @Override
  public Iterator<Constraint> iterator() {
    return null;
  }

  public List<Variable> getVariables() {
    return null;
  }
}
