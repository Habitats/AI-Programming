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
  private List<Variable> variables;

  @Override
  public void run() {
//    Queue<ReviseRequest> queue = initialize();
//    domainFilter(queue);
    domainFilter2();
  }

  //
  // While queue.hasItems() do:
  //    reviseReq = pop(QUEUE)
  //    focalVariable = reviseReq.x
  //    currentConstraint = reviseReq.c
  //    revise(focalVariable, currentConstraint)
  //    If domain(focalVariable) was reduced:
  //      for constrains in constraints:
  //        if constraint == currentConstraint:
  //          continue
  //        if focalVariable not in constraint.variables:
  //          continue
  //        if constraint.focalVariable == focalVariable:
  //          continue
  //
  //        queue.push new reviseReq(focalVariable,constraint)
  //
  private void domainFilter(Queue<ReviseRequest> queue) {
    ReviseRequest todoRevise;
    while ((todoRevise = queue.poll()) != null) {
      revise(todoRevise.getFocalVariable(), todoRevise.getConstraint());
      if (todoRevise.isReduced()) {
        for (Constraint constraint : getConstraints()) {
          if (constraint.equals(todoRevise.getConstraint())) {
            continue;
          } else if (!constraint.contains(todoRevise.getFocalVariable())) {
            continue;
          }
          // nope, this is not right
          else if (constraint.getFocalVariable().equals(todoRevise.getFocalVariable())) {
            continue;
          }
          queue.add(new ReviseRequest(todoRevise.getFocalVariable(), constraint));
        }
      }
    }
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

  // QUEUE = {TODO-REVISE*(Xij, Ci) : for all i,j} where Xij = the jth variable in constraint Ci.
  private ReviseQueue<ReviseRequest> initialize() {
    ReviseQueue<ReviseRequest> queue = new ReviseQueue<>();
    List<Constraint> constraints = getConstraints();
    for (Constraint c : constraints) {
      for (Variable x : c.getVariables()) {
        if (c.contains(x)) {
          queue.add(new ReviseRequest(x, c));
        }
      }
    }
    return queue;
  }

  private List<Constraint> getConstraints() {
    List<Constraint> constraints = testProblem1();
    return constraints;
  }

  public void domainFilter2() {
    List<Constraint> constraints = sudoku();
    Queue<Variable> queue = new PriorityQueue<>();
    queue.addAll(getVariables());
    Variable var;
    while ((var = queue.poll()) != null) {
      Log.v(TAG, "before: " + var);
      for (Constraint constraint : constraints) {
        if (revise(var, constraint)) {
          queue.addAll(constraint.getVariables());
        }
      }

      Log.v(TAG, "after: " + var);
      Log.v(TAG, "------------------------------------");
    }

    for (Variable v : getVariables()) {
      Log.v(TAG, v);
    }
    printSudoku();
  }

  private void printSudoku() {
    String row = "";
    for (Variable v : getVariables()) {
      row += v.getValue();
      if (((getVariables().indexOf(v) + 1) % 4) == 0) {
        Log.v(TAG, row);
        row = "";
      }
    }
  }

  private List<Constraint> testProblem1() {
    List<Constraint> constraints = new ArrayList<>();
    Variable x = new Variable("x", new Domain(1, 2, 3, 4, 5));
    Variable y = new Variable("y", new Domain(1, 2, 3, 4, 5));
    Variable z = new Variable("z", new Domain(1, 2, 3, 4, 5));
    Variable w = new Variable("w", new Domain(1, 2, 3, 4, 5));

    variables = new ArrayList<>();
    variables.add(x);
    variables.add(y);
    variables.add(z);
    variables.add(w);

    Constraint c1 = new Constraint(variables, "x+w +y< z+1");
    constraints.add(c1);
    return constraints;
  }

  private List<Constraint> testProblem2() {
    List<Constraint> constraints = new ArrayList<>();
    Variable x = new Variable("x", new Domain(0, 1, 2, 3, 4, 5, 6));
    Variable y = new Variable("y", new Domain(0, 1, 2, 3, 4, 5, 6));
    Variable z = new Variable("z", new Domain(0, 1, 2, 3, 4, 5, 6));

    variables = new ArrayList<>();
    variables.add(x);
    variables.add(y);
    variables.add(z);

    Constraint c1 = new Constraint(variables, "x == 2*y");
    Constraint c2 = new Constraint(variables, "x > z");
    Constraint c3 = new Constraint(variables, "y < z");
    Constraint c4 = new Constraint(variables, "(y +x + z )== 9");
    constraints.add(c1);
    constraints.add(c2);
    constraints.add(c3);
    constraints.add(c4);
    return constraints;
  }

  private List<Constraint> sudoku() {
    int[] domain = new int[4];
    for (int i = 1; i <= domain.length; i++) {
      domain[i - 1] = i;
    }
    variables = new ArrayList<>();
    for (int x = 1; x <= domain.length; x++) {
      for (int y = 1; y <= domain.length; y++) {
        Variable var = new Variable("v" + x + y, new Domain(domain));
        getVariables().add(var);
      }
    }

    getVariables().get(0).setValue(3);
    getVariables().get(2).setValue(4);
    getVariables().get(5).setValue(1);
    getVariables().get(7).setValue(2);
    getVariables().get(9).setValue(4);
    getVariables().get(11).setValue(3);
    getVariables().get(12).setValue(2);
    getVariables().get(14).setValue(1);
    getVariables().get(15).setValue(4);


    printSudoku();
    List<Constraint> constraints = new ArrayList<>();
    for (Variable var : getVariables()) {
      String col = String.valueOf(var.getId().charAt(1));
      String row = String.valueOf(var.getId().charAt(2));
      Constraint
          c1 =
          new Constraint(getVariables(), String.format(
              "v%s1 != v%s2 and v%s1 != v%s3 and v%s1 != v%s4 and v%s3 != v%s4 and v%s2 != v%s3 and v%s2 != v%s4", //
              col, col, col, col, col, col, col, col, col, col, col, col));
      Constraint
          c2 =
          new Constraint(getVariables(), String.format(
              "v1%s != v2%s and v1%s != v3%s and v1%s != v4%s and v3%s != v4%s and v2%s != v3%s and v2%s != v4%s", //
              row, row, row, row, row, row, row, row, row, row, row, row));
      constraints.add(c1);
      constraints.add(c2);
    }

    Log.v(TAG, "finished creating constraints");

    return constraints;
  }

  public List<Variable> getVariables() {
    return variables;
  }
}
