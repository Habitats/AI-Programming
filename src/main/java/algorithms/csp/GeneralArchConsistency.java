package algorithms.csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import ai.Log;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

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


  public static Result domainFilter(CspPuzzle puzzle) {
    List<Constraint> constraints = puzzle.getConstraints();
    Queue<Variable> queue = new PriorityQueue<>();
    Set<String> queueHash = new HashSet<>();
    queue.addAll(puzzle.getVariables().getAll());
    for (Variable v : puzzle.getVariables()) {
      queueHash.add(v.getId());
    }
    int initialDomainSize = puzzle.getDomainSize();
    Variable focalVariable;
    while ((focalVariable = queue.poll()) != null) {
      queueHash.remove(focalVariable.getId());
      if (focalVariable.getDomain().iEmpty()) {
        return Result.EMPTY_DOMAIN;
      }
//      Log.v(TAG, "before: " + focalVariable);
      for (Constraint constraint : constraints) {

//        // if focalvariable isn't present in the constraint, there is no way it can directly affect it either
        if (!constraint.contains(focalVariable)) {
          continue;
        }

        if (constraint.revise(focalVariable, puzzle)) {
          constraint
              .addVariablesInConstraintsContainingCurrentVariable(puzzle, queue, queueHash, focalVariable, constraint);
//          addVariablesInConstraintsContainingCurrentVariable2(puzzle, queue, queueHash, focalVariable);
        }
      }

//      Log.v(TAG, "after: " + focalVariable);
//      Log.v(TAG, "------------------------------------");
    }

    printVariables(puzzle);
    if (puzzle.getDomainSize() == puzzle.getVariables().size()) {
      return Result.SOLUTION;
    } else if (puzzle.getDomainSize() == initialDomainSize) {
      return Result.UNCHANGED_DOMAIN;
    } else if (puzzle.getDomainSize() < puzzle.getVariables().size()) {
      return Result.EMPTY_DOMAIN;
    } else {
      return Result.SHRUNK_DOMAIN;
    }
  }

  public static void printVariables(CspPuzzle puzzle) {

    List<Variable> sorted = new ArrayList<>(puzzle.getVariables().copy().getAll());

    Collections.sort(sorted, new Comparator<Variable>() {
      @Override
      public int compare(Variable o1, Variable o2) {
        String s1 = o1.getId();
        String s2 = o2.getId();
        return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
      }
    });

    for (Variable variable : sorted) {
      Log.v(TAG, variable.getId() + " - DS: " + variable.getDomain().getSize() + " " + variable);
    }
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
