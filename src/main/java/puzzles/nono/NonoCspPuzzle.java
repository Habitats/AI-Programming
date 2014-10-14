package puzzles.nono;

import ai.models.Node;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;
import algorithms.csp.canonical_utils.VariableList;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 01.10.2014.
 */
public class NonoCspPuzzle<T extends Node<T> & VariableListener> extends SimpleAStarCspPuzzle<T> {


  public NonoCspPuzzle(AStarCsp<T> astarCsp) {
    super(astarCsp);
  }

  @Override
  protected AStarCspPuzzle newInstance() {
    return new NonoCspPuzzle(getAstarCsp());
  }

  @Override
  public VariableList generateVariables() {
    return null;
  }
}
