package algorithms.minimax;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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


  public static MiniMaxState alphaBeta(MiniMaxState node, int depth) {
    node.setAlpha(Integer.MIN_VALUE);
    node.setBeta(Integer.MAX_VALUE);
    return alphaBeta(node, depth, true);
  }

  private static MiniMaxState alphaBeta(MiniMaxState state, int depth, boolean maximizingPlayer) {
    if (depth == 0 || state.isTerminal()) {
      return state;
    }
    if (maximizingPlayer) {
      List<MiniMaxState> possibleNextStates = state.getPossibleNextStates();
      for (MiniMaxState child : possibleNextStates) {
        state.setAlpha(Math.max(state.getAlpha(), alphaBeta(child, depth - 1, false).getBeta()));
        if (state.getBeta() <= state.getAlpha()) {
          break;
        }
      }
      return Collections.max(possibleNextStates, new Comparator<MiniMaxState>() {
        @Override
        public int compare(MiniMaxState o1, MiniMaxState o2) {
          return o1.getAlpha() - o2.getAlpha();
        }
      });
    } else {
      List<MiniMaxState> opposingStates = state.getOpposingStates();
      for (MiniMaxState child : opposingStates) {
        child.setParent(state);
        state.setBeta(Math.min(state.getBeta(), alphaBeta(child, depth - 1, true).getAlpha()));
        if (state.getBeta() <= state.getAlpha()) {
          break;
        }
      }
      return Collections.min(opposingStates, new Comparator<MiniMaxState>() {
        @Override
        public int compare(MiniMaxState o1, MiniMaxState o2) {
          return o1.getBeta() - o2.getBeta();
        }
      });
    }
  }
}
