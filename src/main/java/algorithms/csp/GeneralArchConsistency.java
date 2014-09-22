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
   * Check all permutations of variables and values and isSatisfiable if the expression is isSatisfiable.
   *
   * @return true on the first isSatisfiable occurrence, false is no combination satisfies expression
   */
  private static boolean isSatisfiable(Constraint constraint, int focalVariableIndex, List<Variable> vars,
                                       Variable focalVariable, List<Variable> variables) {
    boolean hasMoreVariables = constraint.hasNext() || vars.size() > focalVariableIndex;
    if (hasMoreVariables) {
      // isSatisfiable if it's the first time we see this variable. If yes, add it to current variables
      if (vars.size() == focalVariableIndex) {
        String id = constraint.getNextVariableId();
        for (Variable var : variables) {
          if (id.equals(var.getId())) {
            vars.add(var);
            break;
          }
        }
      }
      // iterate over all possible values for this variable
      for (Integer val : vars.get(focalVariableIndex).getDomain()) {
        // set a value, and recursively combine it with the possible combinations of the remaining variables
        vars.get(focalVariableIndex).setValue(val);
        if (isSatisfiable(constraint, focalVariableIndex + 1, vars, focalVariable, variables)) {
          return true;
        }
      }
    } else {
      // return on the first isSatisfiable occurrence
      if (constraint.isSatisfied(vars, focalVariable)) {
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

        // if focalvariable isn't present in the constraint, there is no way it can directly affect it either
        if (!constraint.contains(var)) {
          continue;
        }

        if (revise(var, constraint, puzzle.getVariables())) {
          // push all other variables in this constraint onto the queue
          for (Variable varInConstraint : constraint.getVariables()) {
            String variableId = varInConstraint.getId();
            // if it's 'this' variable, do nothing
            if (variableId.equals(var.getId())) {
              continue;
            }
            queue.add(puzzle.getVariable(variableId));
          }
        }
      }

//      Log.v(TAG, "after: " + var);
//      Log.v(TAG, "------------------------------------");
    }

    for (Variable v : puzzle.getVariables()) {
      Log.v(TAG, v);
    }
    if (puzzle.getDomainSize() == puzzle.getVariables().size()) {
      return Result.SOLUTION;
    } else if (puzzle.getDomainSize() == initialDomainSize) {
      return Result.UNCHANGED_DOMAIN;
    } else {
      return Result.SHRUNK_DOMAIN;
    }
  }


  /**
   * Remove all values from focalVariables' domain if no combination of non-focalVariables satisfy the constraint
   *
   * @return return true if domain is reduced by assumption
   */
  private static boolean revise(Variable focalVariable, Constraint constraint, List<Variable> variables) {
    int oldSize = focalVariable.getDomain().getSize();

    // iterate over all the values of the focalDomain
    for (Integer val : focalVariable.getDomain()) {
      focalVariable.setValue(val);

      List<Variable> vars = new ArrayList<>();
      constraint.clearHasNext();
      constraint.removeFocalvariableFromTodo(focalVariable);
      boolean satisfiable = isSatisfiable(constraint, 0, vars, focalVariable, variables);
      if (!satisfiable) {
        // if constraint is impossible to satisfy with the given value, remove the value from the domain
        Log.v(TAG, "reducing the domain of " + focalVariable + " by removing: " + val + ". Violating: " + constraint);
        focalVariable.getDomain().remove(val);
      }
//      else{
//        Log.v(TAG, "unable to reduce the domain of " + focalVariable + " with values:  " + val + " and constraint: " + constraint);
//      }
    }
    int newSize = focalVariable.getDomain().getSize();
//    Log.v(TAG, "old: " + oldSize + " new: " + newSize);
    return oldSize > newSize;
  }
}
