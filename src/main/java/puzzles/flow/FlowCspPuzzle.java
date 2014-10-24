package puzzles.flow;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ai.Log;
import ai.models.grid.Board;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 01.10.2014.
 */
public class FlowCspPuzzle extends SimpleAStarCspPuzzle {


  private static final String TAG = FlowCspPuzzle.class.getSimpleName();

  public FlowCspPuzzle(AStarCsp<FlowTile> astarCsp) {
    super(astarCsp);
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new FlowCspPuzzle(getAstarCsp());
  }

  @Override
  public VariableList generateVariables() {
    if (Flow.SIMPLE) {
      return generateSimpleVariables();
    } else {
      return generateFlowVariables();
    }
  }

  private VariableList generateFlowVariables() {
    VariableList variables = new VariableList();
    Collection<FlowTile> items = getAstarCsp().getAdapter().getItems();
    for (FlowTile colorTile : items) {
      Variable<Integer> colorVariable = new Variable(colorTile.getId(), getInitialDomain());

      // if mid state, add both input and output
      if (colorTile.getColorState() == FlowTile.State.MID) {
        Variable<Integer> inputVariable = new Variable(colorTile.getInput(), getInputDomain(colorTile));
        putVariable(variables, inputVariable);
        Variable<Integer> outputVariable = new Variable(colorTile.getOutput(), getOutputDomain(colorTile));
        putVariable(variables, outputVariable);
      }
      // if mid state, both input and output
      else {
        colorVariable.setAssumption(colorTile.getInitialValue());
        // if start state, only output
        if (colorTile.getColorState() == FlowTile.State.START) {
          Variable<Integer> outputVariable = new Variable(colorTile.getOutput(), getOutputDomain(colorTile));
          putVariable(variables, outputVariable);
        }
        // if end state, only input
        else if (colorTile.getColorState() == FlowTile.State.END) {
          Variable<Integer> inputVariable = new Variable(colorTile.getInput(), getInputDomain(colorTile));
          putVariable(variables, inputVariable);
        }
      }

      putVariable(variables, colorVariable);
      colorVariable.addListener(colorTile);
    }
    return variables;
  }

  private VariableList generateSimpleVariables() {
    VariableList variables = new VariableList();
    Collection<FlowTile> items = getAstarCsp().getAdapter().getItems();
    for (FlowTile colorTile : items) {
      Variable<Integer> colorVariable = new Variable(colorTile.getId(), getInitialDomain());
      putVariable(variables, colorVariable);
      colorVariable.addListener(colorTile);

      if (colorTile.getColorState() != FlowTile.State.MID) {
        colorVariable.setAssumption(colorTile.getInitialValue());
      }
    }
    return variables;
  }

  private Domain getOutputDomain(FlowTile colorTile) {
    Set<Integer> args = new HashSet<>();
    Map<Integer, FlowTile> neighbors = colorTile.getManhattanNeighbors();
    for (Integer index : neighbors.keySet()) {
      FlowTile neighbor = neighbors.get(index);
      if (neighbor.getColorState() == FlowTile.State.MID || neighbor.getColorState() == FlowTile.State.END) {
        args.add(index);
      }
    }
    return new Domain(args);
  }

  private void putVariable(VariableList variables, Variable<Integer> colorVariable) {
    variables.put(colorVariable);
  }

  private Domain getInputDomain(FlowTile colorTile) {
    Set<Integer> args = new HashSet<>();
    Map<Integer, FlowTile> neighbors = colorTile.getManhattanNeighbors();
    for (Integer index : neighbors.keySet()) {
      FlowTile neighbor = neighbors.get(index);
      if (neighbor.getColorState() == FlowTile.State.MID || neighbor.getColorState() == FlowTile.State.START) {
        args.add(index);
      }
    }
    return new Domain(args);
  }

  @Override
  public Variable getSuccessor() {
    return super.getSuccessor();
  }

  @Override
  protected Variable getMinimalDomain(VariableList variables) {
    List<Variable> vars = variables.getAll();

    // sometimes, return something random
//    if (Math.random() > 0.9) {
//      Variable<Integer> randomVar = vars.get((int) ((vars.size() - 1) * Math.random()));
//      if (randomVar.getDomain().getSize() > 1) {
//        return randomVar;
//      }
//    }
    vars = filterOutMidspoints(vars);
    double min = Integer.MAX_VALUE;
    Variable minVar = null;

//    Collections.shuffle(vars);
    for (Variable var : vars) {

      double normalizedSize = var.getDomain().getSize(); // / var.getDomain().getMaxSize();
      if (var.getDomain().getSize() == 1) {
//        var.setValue(var.getDomain().iterator().next());
        continue;
      }
      if (var.getId().startsWith(FlowTile.ID)) {
        continue;
      }
      if (normalizedSize < min) {
        min = normalizedSize;
        minVar = var;
      }
    }
    return minVar;
//    List<Variable> minVars = new ArrayList<>();
//    for (Variable var : vars) {
//      if ((var.getDomain().getSize() / var.getDomain().getMaxSize()) == min) {
//        minVars.add(minVar);
//      }
//    }
//    int index = (int) ((minVars.size() - 1) * Math.random());
//
//    if (index < 0 || minVars.size() == 0) {
//      return minVar;
//    }
//
//    return minVars.get(index);
  }

  private List<Variable> filterOutMidspoints(List<Variable> vars) {
    Board<FlowTile> board = (Board<FlowTile>) getAstarCsp().getAdapter();
    for (Iterator<Variable> iterator = vars.iterator(); iterator.hasNext(); ) {
      Variable var = iterator.next();
      boolean color = var.getId().startsWith(FlowTile.ID);
      boolean output = var.getId().startsWith(FlowTile.OUTPUT);
      boolean input = var.getId().startsWith(FlowTile.INPUT);

      int x = Integer.parseInt(var.getId().split("x")[1].split("y")[0]);
      int y = Integer.parseInt(var.getId().split("y")[1]);

      FlowTile tile = board.get(x, y);

//      if (color) {
//        boolean hasNeighborWithSameColor = false;
//        for (FlowTile neighbor : tile.getManhattanNeighbors().values()) {
//          if (neighbor.getColor().equals(tile.getColor())) {
//            hasNeighborWithSameColor = true;
//          }
//        }
//        if (!hasNeighborWithSameColor) {
//          iterator.remove();
//        }
//      }

    }
    return vars;
  }

  @Override
  public void setAssumption(String variableId, Object value) {
    super.setAssumption(variableId, value);
    if (true) {
      return;
    }

    Variable var = getVariable(variableId);
    Integer val = (Integer) value;

    Domain domain = var.getDomain();
//    Board<FlowTile> board = (Board<FlowTile>) puzzle.getAstarCsp().getAdapter();

    boolean color = var.getId().startsWith(FlowTile.ID);
    boolean input = var.getId().startsWith(FlowTile.INPUT);
    boolean output = var.getId().startsWith(FlowTile.OUTPUT);

    int x = Integer.parseInt(var.getId().split("x")[1].split("y")[0]);
    int y = Integer.parseInt(var.getId().split("y")[1]);

    FlowTile tile = ((Board<FlowTile>) getAstarCsp().getAdapter()).get(x, y);
    Map<Integer, FlowTile> neighbors = tile.getManhattanNeighbors();

    if (output) {
      FlowTile neighbor = neighbors.get(val);

      for (Integer adjNeighborIndex : neighbor.getManhattanNeighbors().keySet()) {
        // if it's this tile
        if ((adjNeighborIndex + 2) % 4 == val) {
          continue;
        }
        FlowTile adjNeighbor = neighbor.getManhattanNeighbors().get(adjNeighborIndex);

        try {
          Variable<Integer>
              adjNeighborOutput =
              getVariable(FlowTile.OUTPUT + adjNeighbor.getId().split(FlowTile.ID)[1]);
          int valueToPrune = (adjNeighborIndex + 2) % 4;
          adjNeighborOutput.getDomain().remove(valueToPrune);
        } catch (Exception e) {
          Log.v(TAG,
                "something went wrong with variable: " + FlowTile.OUTPUT + adjNeighbor.getId().split(FlowTile.ID)[1]);
        }

      }
      Variable neighborInput = getVariable(FlowTile.INPUT + neighbor.getId().split(FlowTile.ID)[1]);
      if (neighborInput != null) {
        neighborInput.setAssumption((val + 2) % 4);
      }
    } else if (input) {
      FlowTile neighbor = neighbors.get(val);

      Variable neighborOutput = getVariable(FlowTile.INPUT + neighbor.getId().split(FlowTile.ID)[1]);

      for (Integer adjNeighborIndex : neighbor.getManhattanNeighbors().keySet()) {
        // if it's this tile
        if ((adjNeighborIndex + 2) % 4 == val) {
          continue;
        }
        FlowTile adjNeighbor = neighbor.getManhattanNeighbors().get(adjNeighborIndex);

        try {
          Variable<Integer> adjNeighborInput = getVariable(FlowTile.INPUT + adjNeighbor.getId().split(FlowTile.ID)[1]);
          int valueToPrune = (adjNeighborIndex + 2) % 4;
          adjNeighborInput.getDomain().remove(valueToPrune);
        } catch (Exception e) {
          Log.v(TAG,
                "something went wrong with variable: " + FlowTile.OUTPUT + adjNeighbor.getId().split(FlowTile.ID)[1]);
        }

      }
      if (neighborOutput != null) {
        neighborOutput.setAssumption((val + 2) % 4);
      }

    }
  }

  @Override
  public int getHeuristic() {

    int domainSize = getDomainSize();

//    int h = 0; for(Variable var: getVariables()){
//     if(var.getDomain().getSize() == 1){
//       h++;
//     }
//    }
//    return  h;
    int bestDomainSize = getVariables().size();
    if (domainSize == bestDomainSize) {
      return 0;
    } else {
      return domainSize - bestDomainSize;
    }
  }

  @Override
  public int getDomainSize() {
    int size = 0;
    for (Variable variable : variables) {
      size += variable.getDomain().getSize();
    }
    return size;
  }
}
