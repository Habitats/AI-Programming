package algorithms.a_star_csp;

import algorithms.csp.GeneralArchConsistency;

/**
 * Created by Patrick on 15.09.2014.
 */
public interface AStarCspPuzzle {

  String getId();

  void visualize();

  void devisualize();
  GeneralArchConsistency duplicate();
}
