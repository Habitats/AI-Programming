package puzzles.flow;

import java.util.Collection;
import java.util.HashSet;
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


  public FlowCspPuzzle(AStarCsp<ColorTile> astarCsp) {
    super(astarCsp);
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new FlowCspPuzzle(getAstarCsp());
  }

  @Override
  public VariableList generateVariables() {
    VariableList variables = new VariableList();
    Collection<ColorTile> items = getAstarCsp().getAdapter().getItems();
    for (ColorTile colorTile : items) {
      Variable<Integer> colorVariable = new Variable(colorTile.getId(), getInitialDomain());

      // if mid state, add both input and output
      if (colorTile.getColorState() == ColorTile.State.MID) {
        Variable<Integer> inputVariable = new Variable(colorTile.getInput(), getInputDomain(colorTile));
        putVariable(variables, inputVariable);
        Variable<Integer> outputVariable = new Variable(colorTile.getOutput(), getOutputDomain(colorTile));
        putVariable(variables, outputVariable);
      }
      // if mid state, both input and output
      else {
        colorVariable.setAssumption(colorTile.getInitialValue());
        // if start state, only output
        if (colorTile.getColorState() == ColorTile.State.START) {
          Variable<Integer> outputVariable = new Variable(colorTile.getOutput(), getOutputDomain(colorTile));
          putVariable(variables, outputVariable);
        }
        // if end state, only input
        else if (colorTile.getColorState() == ColorTile.State.END) {
          Variable<Integer> inputVariable = new Variable(colorTile.getInput(), getInputDomain(colorTile));
          putVariable(variables, inputVariable);
        }
      }

      putVariable(variables, colorVariable);
      colorVariable.setListener(colorTile);
    }
    return variables;
  }

  private Domain getOutputDomain(ColorTile colorTile) {
    Set<Integer> args = new HashSet<>();
    for (Integer index : colorTile.getManhattanNeighbors().keySet()) {
      ColorTile neighbor = colorTile.getManhattanNeighbors().get(index);
      if (neighbor.getColorState() == ColorTile.State.MID || neighbor.getColorState() == ColorTile.State.END) {
        args.add(index);
      }
    }
    return new Domain(args);
  }

  private void putVariable(VariableList variables, Variable<Integer> colorVariable) {
    variables.put(colorVariable);
  }

  private Domain getInputDomain(ColorTile colorTile) {
    Set<Integer> args = new HashSet<>();
    for (Integer index : colorTile.getManhattanNeighbors().keySet()) {
      ColorTile neighbor = colorTile.getManhattanNeighbors().get(index);
      if (neighbor.getColorState() == ColorTile.State.MID || neighbor.getColorState() == ColorTile.State.START) {
        args.add(index);
      }
    }
    return new Domain(args);
  }

  @Override
  public Variable getSuccessor() {
    return getMinimalDomain(getVariables());
  }

  private Variable getMinimalDomain(VariableList variables) {
    int min = Integer.MAX_VALUE;
    Variable minVar = null;
    for (Variable var : variables) {
      if (var.getDomain().getSize() == 1) {
        continue;
      }
      if (var.getDomain().getSize() < min) {
        min = var.getDomain().getSize();
        minVar = var;
      }
    }
    return minVar;
  }
}
