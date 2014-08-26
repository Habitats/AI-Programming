package shortestpath;

import a_star.node.Node;

/**
 * Created by Patrick on 26.08.2014.
 */
public interface AstarCallback {

  void finished(Node best);

  void error();
}
