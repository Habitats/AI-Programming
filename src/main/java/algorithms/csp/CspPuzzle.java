package algorithms.csp;

import java.util.Collection;
import java.util.List;

/**
 * Created by Patrick on 12.09.2014.
 */
public interface CspPuzzle {

  List<Constraint> getConstraints();

  Collection<? extends Variable> getVariables();

  void visualize();
}
