package algorithms.a_star_csp;

import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.09.2014.
 */
public interface AStarCspPuzzle extends CspPuzzle {

  String getId();

  Variable getSuccessor();

  AStarCspPuzzle duplicate();

}
