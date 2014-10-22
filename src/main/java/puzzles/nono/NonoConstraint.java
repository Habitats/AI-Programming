package puzzles.nono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import ai.Log;
import algorithms.csp.CspPuzzle;
import algorithms.csp.GeneralArchConsistency;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;
import puzzles.nono.ChunkVals.V;

import static puzzles.nono.ChunkVals.V.UNCERTAIN;

/**
 * Created by Patrick on 20.10.2014.
 */
public class NonoConstraint extends Constraint<ChunkVals> {

  private static final String TAG = NonoConstraint.class.getSimpleName();
  private final HashMap<String, Variable<ChunkVals>> variablesMap;

  public NonoConstraint(VariableList variables) {
    super(variables);
    variablesMap = new HashMap<>();

    for (Variable var : variables) {
      variablesMap.put(var.getId(), var);
    }
    clearHasNext();
  }

  @Override
  public boolean contains(Variable x) {
    return true;
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

  private boolean isValidState(CspPuzzle puzzle) {
    if (puzzle.getDomainSize() != puzzle.getVariables().size()) {
      return true;
    }
    for (Variable var : puzzle.getVariables()) {
      ChunkVals chunkVals = (ChunkVals) var.getDomain().iterator().next();
      String incomingAxis = String.valueOf(var.getId().charAt(0));
      int varIndex = Integer.parseInt(var.getId().substring(1));

      List<Integer> spec =  //
          incomingAxis.equals("x") ? ((NonoCspPuzzle) puzzle).getColSpecs().get(varIndex)
                                   : ((NonoCspPuzzle) puzzle).getRowSpecs().get(varIndex);

      // check if the number of chunks is consistent with the specs
      if ((incomingAxis + varIndex).equals("y9")) {
        Log.v(TAG, "asd");
      }
      if (chunkVals.getNumChunks() != spec.size()) {
        return false;
      }

      // check if any of the chunks are "too big"
      for (int i = 0; i < spec.size(); i++) {
        if (spec.size() == 2 && spec.get(1) == 2) {
          Log.v(TAG, "wut");
        }
        if (chunkVals.getChunkLength(i) > spec.get(i)) {
          return false;
        }
      }

    }

    return true;
  }

  public void removeInvalidValues(Variable focalVariable, CspPuzzle puzzle) {
    Domain domain = focalVariable.getDomain();
    if (!(domain instanceof NonoDomain)) {
      Log.v(TAG, "wut");
    }
    ChunkVals certainValues = ((NonoDomain) domain).getCertainValues();
    Log.v(TAG, certainValues);
    String incomingAxis = String.valueOf(focalVariable.getId().charAt(0));
    String twinAxis = incomingAxis.equals("x") ? "y" : "x";
    // 0 1 1 1 0 0
    // 0 1 0 1 0 0
    // 0 1 0 1 0 0
    // 0 1 1 1 0 0
    // 0 0 0 1 0 0

    int varIndex = Integer.parseInt(focalVariable.getId().substring(1));
    for (int certainIndex = 0; certainIndex < certainValues.values.size(); certainIndex++) {
      V certainValue = certainValues.values.get(certainIndex);
      if (certainValue == UNCERTAIN) {
        continue;
      }
      Variable<ChunkVals> twin = puzzle.getVariable(twinAxis + certainIndex);
      NonoDomain twinDomain = (NonoDomain) twin.getDomain();
      Iterator<ChunkVals> iterator = twinDomain.iterator();
      while (iterator.hasNext()) {
        ChunkVals vals = iterator.next();
        if (vals.values.get(varIndex) != certainValue) {
          iterator.remove();
        }
      }

    }

    for (Variable var : getVariables()) {
      if (var.getDomain().getSize() == 1) {
        Object next = var.getDomain().iterator().next();
        var.setAssumption(next);
        var.setValue(next);
      }
    }

    // if state is invalid, empty the domain
    if (!isValidState(puzzle)) {
      GeneralArchConsistency.printVariables(puzzle);
      focalVariable.getDomain().empty();
    }

  }

  @Override
  public Iterator<Variable<ChunkVals>> iterator() {
    return getVariables().iterator();
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

  public Map<String, Variable<ChunkVals>> getVariablesMap() {
    return variablesMap;
  }


  public void removeFocalvariableFromTodo(Variable focalVariable) {
    variableIdsToCheck.remove(focalVariable.getId());
  }

  public void clearHasNext() {
    for (Variable var : getVariablesMap().values()) {
      variableIdsToCheck.add(var.getId());
    }
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public List<Variable<ChunkVals>> getVariables() {
    return new ArrayList<>(variablesMap.values());
  }

  @Override
  public boolean isSatisfied(VariableList variables) {
    return false;
  }

}
