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
public class GeneralArchConsistency {

  private GeneralArchConsistency() {
  }


  public enum Result {
    EMPTY_DOMAIN, SHRUNK_DOMAIN, UNCHANGED_DOMAIN, SOLUTION;
  }

  private static final String TAG = GeneralArchConsistency.class.getSimpleName();


  /**
   * Check all permutations of variables and values and check if the expression is satisfied.
   *
   * @return true on the first satisfied occurrence, false is no combination satisfies expression
   */
  private static boolean check(Constraint constraint, int focalVariableIndex, List<Variable> vars,
                               Variable focalVariable) {
    boolean hasMoreVariables = constraint.hasNext() || vars.size() > focalVariableIndex;
    if (hasMoreVariables) {
      // check if it's the first time we see this variable. If yes, add it to current variables
      if (vars.size() == focalVariableIndex) {
        vars.add(constraint.getNextVariable());
      }
      // iterate over all possible values for this variable
      for (Integer val : vars.get(focalVariableIndex).getDomain()) {
        // set a value, and recursively combine it with the possible combinations of the remaining variables
        vars.get(focalVariableIndex).setValue(val);
        if (check(constraint, focalVariableIndex + 1, vars, focalVariable)) {
          return true;
        }
      }
    } else {
      // return on the first satisfied occurrence
      if (constraint.isSatisfied(vars,focalVariable)) {
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
//      Log.v(TAG, "before: " + var);
      for (Constraint constraint : constraints) {
        if (revise(var, constraint)) {
          for (Variable varInConstraint : constraint.getVariables()) {
            if (varInConstraint.getId().equals(var.getId())) {
              queue.add(varInConstraint);
            }
          }
        }
      }

//      Log.v(TAG, "after: " + var);
//      Log.v(TAG, "------------------------------------");
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
      boolean satisfied = check(constraint, 0, vars,focalVariable);
      if (!satisfied) {
        Log.v(TAG, "reducing the domain of " + focalVariable + " by removing: " + val + ". Violating: " + constraint);
        focalVariable.getDomain().remove(val);
      }
    }
    int newSize = focalVariable.getDomain().getSize();
//    Log.v(TAG, "old: " + oldSize + " new: " + newSize);
    return oldSize > newSize;
  }
}
