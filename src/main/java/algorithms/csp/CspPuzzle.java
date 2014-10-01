package algorithms.csp;

import java.util.List;

import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 12.09.2014.
 */
public interface CspPuzzle {



  List<Constraint> getConstraints();

  List<Variable> getVariables();

  int getDomainSize();


  void visualize();

  String getId();

  Variable getVariable(String id);

  List<Variable> generateVariables();

  void setVariables(List<Variable> aVoid);
}
