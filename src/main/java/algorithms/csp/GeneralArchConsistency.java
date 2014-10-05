package algorithms.csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import ai.Log;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 04.09.2014.
 */
public class GeneralArchConsistency {

  private static int numberOfSatisfiedConstraints;

  private GeneralArchConsistency() {
  }

  public static int numberOfNodesWithoutColorAssigned(AStarCspPuzzle puzzle) {
    int count = 0;
    for (Variable var : puzzle.getVariables()) {
      count += var.getDomain().getSize() == 1 ? 0 : 1;
    }
    return count;
  }

  public enum Result {
    EMPTY_DOMAIN, SHRUNK_DOMAIN, UNCHANGED_DOMAIN, SOLUTION
  }

  private static final String TAG = GeneralArchConsistency.class.getSimpleName();


  /**
   * Check all permutations of variables and values and isSatisfiable if the expression is isSatisfiable.
   *
   * @return true on the first isSatisfiable occurrence, false is no combination satisfies expression
   */
  private static boolean isSatisfiable(Constraint constraint, int focalVariableIndex, List<Variable> vars,
                                       Variable focalVariable, VariableList variables) {
    boolean hasMoreVariables = constraint.hasNext() || vars.size() > focalVariableIndex;
    if (hasMoreVariables) {
      // isSatisfiable if it's the first time we see this variable. If yes, put it to current variables
      if (vars.size() == focalVariableIndex) {
        String id = constraint.getNextVariableId();
        vars.add(variables.getVariable(id));
      }
      // iterate over all possible values for this variable
      for (Integer nextValue : vars.get(focalVariableIndex).getDomain()) {
        // put a value, and recursively combine it with the possible combinations of the remaining variables
        vars.get(focalVariableIndex).setValue(nextValue);
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
    Set<String> queueHash = new HashSet<>();
    queue.addAll(puzzle.getVariables().getAll());
    for (Variable v : puzzle.getVariables()) {
      queueHash.add(v.getId());
    }
    int initialDomainSize = puzzle.getDomainSize();
    Variable var;
    while ((var = queue.poll()) != null) {
      queueHash.remove(var.getId());
      if (var.getDomain().iEmpty()) {
        printVariables(puzzle);
        return Result.EMPTY_DOMAIN;
      }
//      Log.v(TAG, "before: " + var);
      for (Constraint constraint : constraints) {

//        // if focalvariable isn't present in the constraint, there is no way it can directly affect it either
        if (!constraint.contains(var)) {
          continue;
        }

        if (revise(var, constraint, puzzle.getVariables())) {
          addVariablesInConstraintsContainingCurrentVariable(puzzle, queue, queueHash, var, constraint);
//          addVariablesInConstraintsContainingCurrentVariable2(puzzle, queue, queueHash, var);
        }
      }

//      Log.v(TAG, "after: " + var);
//      Log.v(TAG, "------------------------------------");
    }

    printVariables(puzzle);
    if (puzzle.getDomainSize() == puzzle.getVariables().size()) {
      return Result.SOLUTION;
    } else if (puzzle.getDomainSize() == initialDomainSize) {
      return Result.UNCHANGED_DOMAIN;
    } else {
      return Result.SHRUNK_DOMAIN;
    }
  }

  private static void printVariables(CspPuzzle puzzle) {
    for (Variable variable : puzzle.getVariables()) {
      Log.v(TAG, variable.getDomain());
    }
  }

  private static void addVariablesInConstraintsContainingCurrentVariable2(CspPuzzle puzzle, Queue<Variable> queue,
                                                                          Set<String> queueHash, Variable var) {
    for (String variableId : var.getVariableIDsInConstraintsContainingVariable()) {
      if (!queueHash.contains(variableId)) {
        queue.add(puzzle.getVariable(variableId));
        queueHash.add(variableId);
      }
    }
  }

  private static void addVariablesInConstraintsContainingCurrentVariable(CspPuzzle puzzle, Queue<Variable> queue,
                                                                         Set<String> queueHash, Variable var,
                                                                         Constraint constraint) {
    for (Constraint constraintContainingVariable : var.getConstraintsContainingVariable()) {
      if (constraintContainingVariable.equals(constraint)) {
        continue;
      }
      for (Variable variableInConstraint : constraintContainingVariable) {
        if (variableInConstraint.getId().equals(var.getId())) {
          continue;
        }
        if (!queueHash.contains(variableInConstraint.getId())) {
          queue.add(puzzle.getVariable(variableInConstraint.getId()));
          queueHash.add(variableInConstraint.getId());
        }
      }
    }
  }

  /**
   * Remove all values from focalVariables' domain if no combination of non-focalVariables satisfy the constraint
   *
   * @return return true if domain is reduced by assumption
   */

  private static boolean revise(Variable focalVariable, Constraint constraint, VariableList variables) {
    int oldSize = focalVariable.getDomain().getSize();

    // iterate over all the values of the focalDomain
    Iterator<Integer> iterator = focalVariable.getDomain().iterator();
    while (iterator.hasNext()) {
      Integer val = iterator.next();
      focalVariable.setValue(val);

      List<Variable> vars = new ArrayList<>();
      constraint.clearHasNext();
      constraint.removeFocalvariableFromTodo(focalVariable);
      boolean satisfiable = isSatisfiable(constraint, 0, vars, focalVariable, variables);
      if (!satisfiable) {
        // if constraint is impossible to satisfy with the given value, remove the value from the domain
        Log.v(TAG, "reducing the domain of " + focalVariable + " by removing: " + val + ". Violating: " + constraint);
        iterator.remove();
      }
//      else{
//        Log.v(TAG, "unable to reduce the domain of " + focalVariable + " with values:  " + val + " and constraint: " + constraint);
//      }
    }
    int newSize = focalVariable.getDomain().getSize();
//    Log.v(TAG, "old: " + oldSize + " new: " + newSize);
    return oldSize > newSize;
  }

  public static int numberOfUnsatisfiedConstraints(CspPuzzle puzzle) {
    int num = 0;
    puzzle.visualize();
    for (Constraint constraint : puzzle.getConstraints()) {
      boolean satisfied = constraint.isSatisfied(puzzle.getVariables());
      num += satisfied ? 0 : 1;
      if (!satisfied) {
        Log.v(TAG, constraint);
      }
    }
    return num;
  }
}
