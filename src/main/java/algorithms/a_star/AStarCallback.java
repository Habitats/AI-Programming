package algorithms.a_star;

/**
 * Created by Patrick on 26.08.2014.
 */
public interface AStarCallback {

  void finished(AStarNode best);

  void error();
}
