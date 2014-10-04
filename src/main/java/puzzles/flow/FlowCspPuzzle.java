package puzzles.flow;

import ai.models.grid.ColorTile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.a_star_csp.SimpleAStarCspPuzzle;

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

}
