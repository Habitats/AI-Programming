package algorithms.minimax;

/**
 * Created by anon on 31.10.2014.
 */
public class MiniMax {

//      function alphabeta(node, depth, α, β, maximizingPlayer)
//           if depth = 0 or node is setAlpha terminal node
//               return the heuristic value of node
//           if maximizingPlayer
//               for each child of node
//                   α := max(α, alphabeta(child, depth - 1, α, β, FALSE))
//                   if β ≤ α
//                       break (* β cut-off *)
//               return α
//           else
//               for each child of node
//                   β := min(β, alphabeta(child, depth - 1, α, β, TRUE))
//                   if β ≤ α
//                      break (* α cut-off *)
//               return β


  public static int alphaBeta(MiniMaxState node, int depth) {
    return alphaBeta(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
  }

  private static int alphaBeta(MiniMaxState state, int depth, int a, int b, boolean maximizingPlayer) {
    if (depth == 0 || state.isTerminal()) {
      return state.getScore();
    }
    if (maximizingPlayer) {
      for (MiniMaxState child : state.getPossibleNextStates()) {
        child.setParent(state);
        a = Math.max(a, alphaBeta(child, depth - 1, a, b, false));
        if (b <= a) {
          break;
        }
      }
      return a;
    } else {
      for (MiniMaxState child : state.getOpposingStates()) {
        child.setParent(state);
        b = Math.min(b, alphaBeta(child, depth - 1, a, b, true));
        if (b <= a) {
          break;
        }
      }
      return b;
    }
  }

}
