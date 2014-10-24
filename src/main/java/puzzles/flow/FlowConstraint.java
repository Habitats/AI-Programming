package puzzles.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by anon on 10.10.2014.
 */
public class FlowConstraint extends Constraint<Integer> {

  private static final String TAG = FlowConstraint.class.getSimpleName();
  private final Map<String, Variable<Integer>> variablesMap;
  private final String expression;

  public FlowConstraint(VariableList variables, String expression) {
    super(variables);
    variablesMap = new HashMap<>();
    this.expression = expression;

    for (Variable var : variables) {
      if (expression.contains(var.getId())) {
        variablesMap.put(var.getId(), var);
      }
    }
    clearHasNext();
  }


  public Map<String, Variable<Integer>> getVariablesMap() {
    return variablesMap;
  }


  public boolean hasNext() {
    return variableIdsToCheck.size() > 0;
  }

  public String getNextVariableId() {
    String next = variableIdsToCheck.iterator().next();
    variableIdsToCheck.remove(next);
    return next;

  }

  @Override
  public boolean revise(Variable focalVariable, CspPuzzle puzzle) {
    int oldSize = puzzle.getDomainSize();
    removeInvalidValues(focalVariable, puzzle);
    int newSize = puzzle.getDomainSize();
    return oldSize > newSize;

  }

  private void removeInvalidValues(Variable focalVariable, CspPuzzle puzzle) {
    Domain domain = focalVariable.getDomain();
//    Board<FlowTile> board = (Board<FlowTile>) puzzle.getAstarCsp().getAdapter();

    boolean color = focalVariable.getId().startsWith(FlowTile.ID);
    boolean input = focalVariable.getId().startsWith(FlowTile.INPUT);
    boolean output = focalVariable.getId().startsWith(FlowTile.OUTPUT);


    int x = Integer.parseInt(focalVariable.getId().split("x")[1].split("y")[0]);
    int y = Integer.parseInt(focalVariable.getId().split("y")[1]);



//    FlowTile tile = board.get(x, y);
  }


  public void removeFocalvariableFromTodo(Variable focalVariable) {
    variableIdsToCheck.remove(focalVariable.getId());
  }

  @Deprecated
  public boolean isSatisfied(VariableList variables) {
    return true;
  }


  public boolean contains(Variable x) {
    return contains(x);
  }

  @Override
  public void addVariablesInConstraintsContainingCurrentVariable(CspPuzzle puzzle, Queue<Variable> queue,
                                                                 Set<String> queueHash, Variable var,
                                                                 Constraint constraint) {
    for (Variable v : puzzle.getVariables()) {
      if (!queueHash.contains(v)) {
        queue.add(v);
      }
      queueHash.add(v.getId());
    }

  }

  @Override
  public Iterator<Variable<Integer>> iterator() {
    return getVariablesMap().values().iterator();
  }

  public void clearHasNext() {
    for (Variable var : getVariablesMap().values()) {
      variableIdsToCheck.add(var.getId());
    }
  }

  @Override
  public String toString() {
    return "yo";
  }

  public List<Variable<Integer>> getVariables() {
    return new ArrayList<>(getVariablesMap().values());
  }

}
