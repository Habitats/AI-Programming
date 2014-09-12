package algorithms.csp;

import java.util.List;

/**
 * Created by Patrick on 12.09.2014.
 */
public interface CspPuzzle {

  List<Constraint> getConstraints();

  List<Variable> getVariables();

  void visualize();
}
