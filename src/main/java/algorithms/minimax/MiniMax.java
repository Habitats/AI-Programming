package algorithms.minimax;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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


  public static MiniMaxState alphaBeta(MiniMaxState node, int depth, List<MiniMaxState> endStates) {
    node.setAlpha(Integer.MIN_VALUE);
    node.setBeta(Integer.MAX_VALUE);
    return alphaBeta(node, depth, true, endStates);
  }

  private static MiniMaxState alphaBeta(MiniMaxState state, int depth, boolean maximizingPlayer,
                                        List<MiniMaxState> endStates) {
    if (depth == 0 || state.isTerminal()) {
      endStates.add(state);
      return state;
    }
    if (maximizingPlayer) {
      for (MiniMaxState child : state.getPossibleNextStates()) {
        child.setParent(state);
        state.setAlpha(Math.max(state.getAlpha(), alphaBeta(child, depth - 1, false, endStates).getScore()));
        if (state.getBeta() <= state.getAlpha()) {
          break;
        }
      }
      return state;
    } else {
      for (MiniMaxState child : state.getOpposingStates()) {
        child.setParent(state);
        state.setBeta(Math.min(state.getBeta(), alphaBeta(child, depth - 1, true, endStates).getScore()));
        if (state.getBeta() <= state.getAlpha()) {
          break;
        }
      }
      return state;
    }
  }

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

  public static List<MiniMaxState> getSearchTree(MiniMaxState state, int maxPly) {
    return getSearchTree(state, maxPly, 0, new LinkedList<>());
  }

  public static List<MiniMaxState> getSearchTree(MiniMaxState state, int maxPly, int currentPly,
                                                 List<MiniMaxState> states) {

    // for all your possible next moves
    List<MiniMaxState> nextStates = state.getPossibleNextStates();
    for (MiniMaxState next : nextStates) {
      next.setParent(state);
      // for all the possible opposing answers to this move
      List<MiniMaxState> opposingStates = next.getOpposingStates();
      for (MiniMaxState opposingState : opposingStates) {
        opposingState.setParent(next);
        // if this is the last ply, generate the next moves, and return them
        if (currentPly == maxPly) {
          List<MiniMaxState> endStates = opposingState.getPossibleNextStates();
          for (MiniMaxState endState : endStates) {
            endState.setParent(opposingState);
            states.add(endState);
          }
        } else {
          getSearchTree(opposingState, maxPly, currentPly + 1, states);
        }
      }
    }

    return states;
  }

  public static MiniMaxState getBestState(List<MiniMaxState> tree) {
    Queue<MiniMaxState> stateScores = new PriorityQueue<>();
    for (MiniMaxState state : tree) {
      state.getScore();
      stateScores.add(state);
    }
    return stateScores.poll();
  }

  public static MiniMaxState getOriginState(MiniMaxState state) {
    while ((state.getParent().hasParent())) {
      state = state.getParent();
    }
    return state;
  }
}
