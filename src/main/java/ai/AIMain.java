package ai;

import puzzles.graph_coloring.GraphColoring;
import puzzles.shortestpath.ShortestPath;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AIMain {

  public static final String TAG = AIMain.class.getSimpleName();

  public static void main(String[] args) {
   graphColoring();
//    shortestPath();
  }

  public static void shortestPath() {
    new ShortestPath();
  }

  public static void graphColoring() {
    new GraphColoring();
  }
}
