package algorithms.csp;

import java.util.List;

import ai.models.AIAdapter;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 12.09.2014.
 */
public interface CspPuzzle {


  void setAdapter(AIAdapter graph);

  List<Constraint> getConstraints();

  List<Variable> getVariables();

  int getDomainSize();


  void visualize();

  String getId();

  Variable getVariable(String id);

}
