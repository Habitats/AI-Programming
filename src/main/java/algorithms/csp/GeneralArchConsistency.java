package algorithms.csp;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.Log;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 04.09.2014.
 */
public class GeneralArchConsistency  {

  private GeneralArchConsistency() {
  }



  public enum Result {
    EMPTY_DOMAIN, SHRUNK_DOMAIN, UNCHANGED_DOMAIN, SOLUTION;
  }

  private static final String TAG = GeneralArchConsistency.class.getSimpleName();


  private static boolean check(Constraint constraint, int index, List<Variable> vars) {
    if (constraint.hasNext() || vars.size() > index) {
      if (vars.size() == index) {
        vars.add(constraint.getNextVariable());
      }
      for (Integer val : vars.get(index).getDomain()) {
        vars.get(index).setValue(val);
        if (check(constraint, index + 1, vars)) {
          return true;
        }
      }
    } else {
      if (constraint.isSatisfied(vars)) {
        return true;
      }
    }
    return false;
  }

  public static Result domainFilter(CspPuzzle puzzle) {
    List<Constraint> constraints = puzzle.getConstraints();
    Queue<Variable> queue = new PriorityQueue<>();
    queue.addAll(puzzle.getVariables());
    int initialDomainSize = puzzle.getDomainSize();
    Variable var;
    while ((var = queue.poll()) != null) {
      if (var.getDomain().iEmpty()) {
        return Result.EMPTY_DOMAIN;
      }
      Log.v(TAG, "before: " + var);
      for (Constraint constraint : constraints) {
        if (revise(var, constraint)) {
          for (Variable varInConstraint : constraint.getVariables()) {
            if (varInConstraint.getId().equals(var.getId())) {
              queue.add(varInConstraint);
            }
          }
        }
      }

      Log.v(TAG, "after: " + var);
      Log.v(TAG, "------------------------------------");
    }

    for (Variable v : puzzle.getVariables()) {
      Log.v(TAG, v);
    }
    if (puzzle.getDomainSize() == initialDomainSize) {
      return Result.UNCHANGED_DOMAIN;
    } else if (puzzle.getDomainSize() == puzzle.getVariables().size()) {
      return Result.SOLUTION;
    } else {
      return Result.SHRUNK_DOMAIN;
    }
  }


  /**
   * @return return true if assumption satisfies constraint
   */
  private static boolean revise(Variable focalVariable, Constraint constraint) {
    int oldSize = focalVariable.getDomain().getSize();
    constraint.setFocalVariable(focalVariable);

    if (!constraint.contains(focalVariable)) {
      return false;
    }
    for (Integer val : focalVariable.getDomain()) {
      focalVariable.setValue(val);

      List<Variable> vars = new ArrayList<>();
      constraint.clearHasNext();
      boolean satisfied = check(constraint, 0, vars);
      if (!satisfied) {
        focalVariable.getDomain().remove(val);
      }
    }
    int newSize = focalVariable.getDomain().getSize();
    Log.v(TAG, "old: " + oldSize + " new: " + newSize);
    return oldSize > newSize;
  }
}
