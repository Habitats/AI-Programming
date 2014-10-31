package algorithms.minimax;

import java.util.List;

/**
 * Created by anon on 31.10.2014.
 */
public interface MiniMaxState extends Comparable<MiniMaxState>{

  void generatePossibleNextStates();

  void generateOpposingStates();

  void setParent(MiniMaxState state);

  int getScore();

  MiniMaxState getParent();

  boolean hasParent();

  List<MiniMaxState> getPossibleNextStates();

  List<MiniMaxState> getOpposingStates();

  boolean isTerminal();

  int getAlpha();

  int getBeta();

  void setAlpha(int a);

  void setBeta(int b);

}
