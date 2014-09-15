package algorithms.csp;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.Log;
import algorithms.a_star_csp.AStarConstraintSatisfactionPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 04.09.2014.
 */
public class GeneralArchConsistency implements AStarConstraintSatisfactionPuzzle {


  @Override
  public String getId() {
    return puzzle.getId();
  }

  @Override
  public void visualize() {
    puzzle.visualize();
  }

  @Override
  public void devisualize() {

  }

  public Variable getSuccessor() {
    int max = Integer.MIN_VALUE;
    Variable maxVar = null;
    for (Variable var : puzzle.getVariables()) {
      if (var.getDomain().getSize() > max) {
        max = var.getDomain().getSize();
        maxVar = var;
      }
    }
    return maxVar;
  }

  @Override
  public GeneralArchConsistency duplicate() {
    GeneralArchConsistency dupe = new GeneralArchConsistency(puzzle.duplicate());

    return dupe;
  }

  public int getNumVariables() {
    return puzzle.getVariables().size();
  }


  public enum Result {
    EMPTY_DOMAIN, SHRUNK_DOMAIN, UNCHANGED_DOMAIN, SOLUTION;
  }

  private static final String TAG = GeneralArchConsistency.class.getSimpleName();
  private final CspPuzzle puzzle;

  public GeneralArchConsistency(CspPuzzle puzzle) {
    this.puzzle = puzzle;
  }


  private boolean check(Constraint constraint, int index, List<Variable> vars) {
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
      if (constraint.isSatisfied()) {
        return true;
      }
    }
    return false;
  }

  public Result domainFilter() {
    List<Constraint> constraints = puzzle.getConstraints();
    Queue<Variable> queue = new PriorityQueue<>();
    queue.addAll(puzzle.getVariables());
    int initialDomainSize = getDomainSize();
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
    if (getDomainSize() == initialDomainSize) {
      return Result.UNCHANGED_DOMAIN;
    } else if (getDomainSize() == puzzle.getVariables().size()) {
      return Result.SOLUTION;
    } else {
      return Result.SHRUNK_DOMAIN;
    }
  }

  public int getDomainSize() {
    int size = 0;
    for (Variable variable : puzzle.getVariables()) {
      size += variable.getDomain().getSize();
    }
    return size;
  }

  /**
   * @return return true if assumption satisfies constraint
   */
  private boolean revise(Variable focalVariable, Constraint constraint) {
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
