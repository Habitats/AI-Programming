package puzzles.flow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ai.models.grid.ColorTile;
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


  public FlowCspPuzzle(AStarCsp<FlowTile> astarCsp) {
    super(astarCsp);
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new FlowCspPuzzle(getAstarCsp());
  }

  @Override
  public VariableList generateVariables() {
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
  protected Variable getMinimalDomain(VariableList variables) {
    List<Variable<Integer>> vars = variables.getAll();
    // sometimes, return something random
    if (Math.random() > 0.5) {
      Variable<Integer> randomVar = vars.get((int) ((vars.size() - 1) * Math.random()));
      if (randomVar.getDomain().getSize() > 1) {
        return randomVar;
      }
    } int min = Integer.MAX_VALUE;
    Variable minVar = vars.get(0);
//    Collections.shuffle(vars);
    for (Variable var : vars) {

      if (var.getDomain().getSize() == 1) {
        continue;
      }
      if (var.getDomain().getSize() < min) {
        min = var.getDomain().getSize();
        minVar = var;
      }
    }
    List<Variable> minVars = new ArrayList<>();
    for (Variable var : vars) {
      if (var.getDomain().getSize() == min) {
        minVars.add(minVar);
      }
    }
    int index = (int) ((minVars.size() - 1) * Math.random());
    if (index < 0 || minVars.size() == 0) {
      return minVar;
    }

    return minVars.get(index);
  }

  @Override
  public int getHeuristic() {
    Collection<FlowTile> tiles = getAstarCsp().getAdapter().getItems();
    int penalty = 0;
    for (FlowTile tile : tiles) {
      if (!tile.getColor().equals(ColorTile.EMPTY)) {
        int goal;
        if (tile.getColorState() == FlowTile.State.END || tile.getColorState() == FlowTile.State.START) {
          goal = 1;
        } else {
          goal = 2;
        }
        List<FlowTile> sameColorNeighbors = tile.getSameColorNeighbor();
        // penalize if too many neighbors
        if (sameColorNeighbors.size() != goal) {
          penalty += 30;
        }
      } else {
//        penalty += 10;
      }
    }
    int domainSize = getDomainSize();
    int bestDomainSize = getVariables().size();
    if (domainSize == bestDomainSize) {
      return 0;
    } else {
      return domainSize - bestDomainSize + penalty;
    }
  }

  @Override
  public int getDomainSize() {

    int size = 0;
    for (Variable variable : variables) {
      int multiplier;
      if (variable.getId().startsWith(FlowTile.ID)) {
        multiplier = 1;
      } else {
        multiplier = 1;
      }
      size += variable.getDomain().getSize() * multiplier;
    }
    return size;
  }
}
