package algorithms.csp;

import java.util.List;

import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 12.09.2014.
 */
public interface CspPuzzle {


  List<Constraint> getConstraints();

  VariableList getVariables();

  int getDomainSize();


  void visualize();

  String getId();

  Variable getVariable(String id);

  VariableList generateVariables();

  void setVariables(VariableList variables);

  void setAssumption(String id, Integer value);
}
