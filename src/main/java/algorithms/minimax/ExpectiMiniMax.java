package algorithms.minimax;

import java.util.List;

/**
 * Created by Patrick on 23.11.2014.
 */
public class ExpectiMiniMax {

//  function expectiminimax(node, depth)
//    if node is a terminal node or depth = 0
//      return the heuristic value of node
//    if the adversary is to play at node
//      // Return value of minimum-valued child node
//      let α := +∞
//      foreach child of node
//        α := min(α, expectiminimax(child, depth-1))
//    else if we are to play at node
//      // Return value of maximum-valued child node
//      let α := -∞
//      foreach child of node
//        α := max(α, expectiminimax(child, depth-1))
//    else if random event at node
//      // Return weighted average of all child nodes' values
//      let α := 0
//      foreach child of node
//        α := α + (Probability[child] * expectiminimax(child, depth-1))
//    return α


  public static int expectiMax(MiniMaxState node, int depth) {
    return expectiMax(node, depth, true);
  }

  private static int expectiMax(MiniMaxState node, int depth, boolean randomEvent) {
    if (depth == 0 || node.isTerminal()) {
      return node.getScore();
    }
    int a;
    if (!randomEvent) {
      a = Integer.MIN_VALUE;
      a = Math.max(a, expectiMax(node, depth - 1, !randomEvent));
    } else {
      a = 0;
      List<MiniMaxState> allOpposingStates = node.getAllOpposingStates();
      for (MiniMaxState child : allOpposingStates) {
        a += child.getProbability() * expectiMax(child, depth - 1, !randomEvent);
      }
    }
    return a;
  }
}
