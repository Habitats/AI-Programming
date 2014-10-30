package puzzles.flow;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
      Variable<Integer> inputVariable = null;
      Variable<Integer> outputVariable = null;

      // if mid state, add both input and output
      if (colorTile.getColorState() == FlowTile.State.MID) {
        inputVariable = new Variable(colorTile.getInput(), getInputDomain(colorTile));
        putVariable(variables, inputVariable);
        outputVariable = new Variable(colorTile.getOutput(), getOutputDomain(colorTile));
        putVariable(variables, outputVariable);
      }
      // if mid state, both input and output
      else {
        colorVariable.setAssumption(colorTile.getInitialValue());
        // if start state, only output
        if (colorTile.getColorState() == FlowTile.State.START) {
          outputVariable = new Variable(colorTile.getOutput(), getOutputDomain(colorTile));
          putVariable(variables, outputVariable);
        }
        // if end state, only input
        else if (colorTile.getColorState() == FlowTile.State.END) {
          inputVariable = new Variable(colorTile.getInput(), getInputDomain(colorTile));
          putVariable(variables, inputVariable);
        }
      }

      putVariable(variables, colorVariable);
      colorVariable.addListener(colorTile);
      if (outputVariable != null) {
        outputVariable.addListener(colorTile);
      }
      if (inputVariable != null) {
        inputVariable.addListener(colorTile);
      }
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

    double min = Integer.MAX_VALUE;
    Variable minVar = null;

    for (Variable var : vars) {

      double normalizedSize = var.getDomain().getSize(); // / var.getDomain().getMaxSize();
      if (var.getDomain().getSize() == 1) {
        continue;
      }
      if (var.getId().startsWith(FlowTile.ID) && !Flow.SIMPLE) {
        continue;
      }
      if (normalizedSize < min) {
        min = normalizedSize;
        minVar = var;
      }
    }
    return minVar;
  }

  @Override
  public int getHeuristic() {

    int domainSize = getDomainSize();

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
