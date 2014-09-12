package algorithms.csp;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.Log;

/**
 * Created by Patrick on 04.09.2014.
 */
public class GAC implements Runnable {

  private static final String TAG = GAC.class.getSimpleName();
  private final CspPuzzle puzzle;

  public GAC(CspPuzzle puzzle) {
    this.puzzle = puzzle;
  }

  @Override
  public void run() {
//    CspPuzzle puzzle = new Sudoku();
//    csppuzzle puzzle = new testgacproblem(1);
//    CspPuzzle puzzle = new TestGACProblem(2);

    if (domainFilter(puzzle)) {
      Log.v(TAG, "success!");
      puzzle.visualize();
    } else {
      Log.v(TAG, "fail!");
    }
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

  public boolean domainFilter(CspPuzzle puzzle) {
    List<Constraint> constraints = puzzle.getConstraints();
    Queue<Variable> queue = new PriorityQueue<>();
    queue.addAll(puzzle.getVariables());
    Variable var;
    while ((var = queue.poll()) != null) {
      if (var.getDomain().iEmpty()) {
        return false;
      }
      Log.v(TAG, "before: " + var);
      for (Constraint constraint : constraints) {
        if (revise(var, constraint)) {
          queue.addAll(constraint.getVariables());
        }
      }

      Log.v(TAG, "after: " + var);
      Log.v(TAG, "------------------------------------");
    }

    for (Variable v : puzzle.getVariables()) {
      Log.v(TAG, v);
    }
    for(Variable var2 : puzzle.getVariables()){
      if(var2.getDomain().getSize() > 1){
        return false;
      }
    }
    return true;
  }

  /**
   * @return return true if assumption satisfies constraint
   */
  private boolean revise(Variable focalVariable, Constraint constraint) {
    int oldSize = focalVariable.getDomain().getSize();
    constraint.setFocalVariable(focalVariable);
    for (Integer val : focalVariable.getDomain()) {
      focalVariable.setValue(val);

      List<Variable> vars = new ArrayList<>();
      constraint.clearHasNext();
      boolean satisfied = check(constraint, 0, vars);
      if (!satisfied) {
        focalVariable.getDomain().remove(val);
      }
    }
    return oldSize > focalVariable.getDomain().getSize();
  }
  //  // QUEUE = {TODO-REVISE*(Xij, Ci) : for all i,j} where Xij = the jth variable in constraint Ci.
//  private ReviseQueue<ReviseRequest> initialize() {
//    ReviseQueue<ReviseRequest> queue = new ReviseQueue<>();
//    List<Constraint> constraints = getConstraints();
//    for (Constraint c : constraints) {
//      for (Variable x : c.getVariables()) {
//        if (c.contains(x)) {
//          queue.add(new ReviseRequest(x, c));
//        }
//      }
//    }
//    return queue;
//  }
  //  //
  //  // While queue.hasItems() do:
  //  //    reviseReq = pop(QUEUE)
  //  //    focalVariable = reviseReq.x
  //  //    currentConstraint = reviseReq.c
  //  //    revise(focalVariable, currentConstraint)
  //  //    If domain(focalVariable) was reduced:
  //  //      for constrains in constraints:
  //  //        if constraint == currentConstraint:
  //  //          continue
  //  //        if focalVariable not in constraint.variables:
  //  //          continue
  //  //        if constraint.focalVariable == focalVariable:
  //  //          continue
  //  //
  //  //        queue.push new reviseReq(focalVariable,constraint)
  //  //
  //  private void domainFilter(Queue<ReviseRequest> queue) {
  //    ReviseRequest todoRevise;
  //    while ((todoRevise = queue.poll()) != null) {
  //      revise(todoRevise.getFocalVariable(), todoRevise.getConstraint());
  //      if (todoRevise.isReduced()) {
  //        for (Constraint constraint : getConstraints()) {
  //          if (constraint.equals(todoRevise.getConstraint())) {
  //            continue;
  //          } else if (!constraint.contains(todoRevise.getFocalVariable())) {
  //            continue;
  //          }
  //          // nope, this is not right
  //          else if (constraint.getFocalVariable().equals(todoRevise.getFocalVariable())) {
  //            continue;
  //          }
  //          queue.add(new ReviseRequest(todoRevise.getFocalVariable(), constraint));
  //        }
  //      }
  //    }
  //  }
}
