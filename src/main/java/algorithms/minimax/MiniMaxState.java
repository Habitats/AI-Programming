package algorithms.minimax;

import java.util.List;

/**
 * Created by anon on 31.10.2014.
 */
public interface MiniMaxState extends Comparable<MiniMaxState> {


  void setParent(MiniMaxState state);

  int getScore();

  MiniMaxState getParent();


  List<MiniMaxState> getPossibleNextStates();

  List<MiniMaxState> getOpposingStates();

  List<MiniMaxState> getAllOpposingStates();

  boolean isTerminal();


  double getProbability();


}
