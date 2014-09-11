package algorithms.csp;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Patrick on 04.09.2014.
 */
public class GAC implements Runnable {

  @Override
  public void run() {
    Queue<ReviseRequest> queue = initialize();
    domainFilter(queue);
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
    constraint.setFocalVariable(focalVariable);
    Variable var1 = null;
    Variable var2 = null;
    Variable var3 = null;
    Variable var4 = null;
    Variable var5 = null;
    if (constraint.hasNext() || var1 != null) {
      if (var1 == null) {
        var1 = constraint.getFocalVariable();
      }
      for (Integer val : var1.getDomain()) {
        var1.setValue(val);

        if (constraint.hasNext() || var2 != null) {
          if (var2 == null) {
            var2 = constraint.getNextVariable();
          }
          for (Integer val2 : var2.getDomain()) {
            var2.setValue(val2);

            if (constraint.hasNext() || var3 != null) {
              if (var3 == null) {
                var3 = constraint.getNextVariable();
              }
              for (Integer val3 : var3.getDomain()) {
                var3.setValue(val3);

                if (constraint.hasNext() || var4 != null) {
                  if (var4 == null) {
                    var4 = constraint.getNextVariable();
                  }
                  for (Integer val4 : var4.getDomain()) {
                    var4.setValue(val4);

                    var5 = getVariable(constraint, var5);
                  }
                } else {
                  constraint.isSatisfied();
                }
              }
            } else {
              constraint.isSatisfied();
            }
          }
        } else {
          constraint.isSatisfied();
        }
      }
    } else {
      constraint.isSatisfied();
    }
    return false;
  }

  private Variable getVariable(Constraint constraint, Variable var5) {
    if (constraint.hasNext() || var5 != null) {
      if (var5 == null) {
        var5 = constraint.getNextVariable();
      }
      for (Integer val5 : var5.getDomain()) {
        var5.setValue(val5);
      }
    } else {
      constraint.isSatisfied();
    }
    return var5;
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
    List<Constraint> constraints = new ArrayList<>();
    Variable x = new Variable("x", new Domain(1, 2, 3, 4, 5));
    Variable y = new Variable("y", new Domain(1, 2, 3, 4, 5));
    Variable z = new Variable("z", new Domain(1, 2, 3, 4, 5));
    Variable w = new Variable("w", new Domain(1, 2, 3, 4, 5));

    List<Variable> variables = new ArrayList<>();
    variables.add(x);
//    variables.add(y);
    variables.add(z);
    variables.add(w);

    Constraint c1 = new Constraint(variables, "x+w < z+1");
    constraints.add(c1);

    revise(x, c1);

    return constraints;
  }
}
