package puzzles.flow;

import java.util.Iterator;
import java.util.List;

import ai.models.grid.ColorTile;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by anon on 11.10.2014.
 */
public class FlowConstraint extends Constraint<ColorTile> {

  public FlowConstraint(VariableList variables) {
    super(variables);

//    for(Variable var : variables){
//      if(var in)
//    }
  }

  @Override
  public boolean contains(Variable x) {
    return false;
  }

  @Override
  public Iterator<Variable<ColorTile>> iterator() {
    return null;
  }

  @Override
  public boolean isSatisfied(List<Variable<ColorTile>> variables, Variable<ColorTile> focalVariable) {
    return false;
  }

  @Override
  public void clearHasNext() {

  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public List<Variable<ColorTile>> getVariables() {
    return null;
  }

  @Override
  public boolean isSatisfied(VariableList variables) {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public String getNextVariableId() {
    return null;
  }

  @Override
  public void removeFocalvariableFromTodo(Variable<ColorTile> focalVariable) {

  }
}
