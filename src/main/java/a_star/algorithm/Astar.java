package a_star.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import a_star.node.Node;
import aiprog.Log;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Astar {

  private static final String TAG = Astar.class.getSimpleName();
  private Queue<Node> opened;
  private int count;
  private Map<String, Node> generated;
  private boolean dfs = false;
  private boolean bfs = false;

  public Node search(Node start, Node goal) {
    start.generateHeuristic(goal);

    opened = new PriorityQueue<Node>();
    opened.add(start);

    // using a hash of all generated id's for faster lookup
    generated = new HashMap<String, Node>();

    // this is used for BFS/DFS
    count = 0;

    Node current;
    while ((current = opened.poll()) != null) {

//      current.visualize();
      Log.v(TAG, current);

      current.setClosed();

      if (current.isSolution()) {
        return current;
      }

      for (Node child : current.getChildren()) {
        child.setParent(current);
        // if child has already been generated, use that child
        if (generated.containsKey(child.getState())) {
          child = generated.get(child.getState());
        }

        // if child is new
        if (!generated.containsKey(child.getState())) {
          generated.put(child.getState(), child);
          attachAndEvaluate(child, current, goal);
          child.setCount(count);

          if (dfs) {
            count -= 1;
          } else if (bfs) {
            count += 1;
          }

          opened.add(child);
        }

        // else if child has previously been generated, see if it is better
        else if (current.g() + child.costFrom(current) < child.g()) {
          attachAndEvaluate(child, current, goal);
          if (child.isClosed()) {
            propagatePathImprovement(child);
          }
        }
      }
    }

    return null;
  }

  private void attachAndEvaluate(Node child, Node parent, Node goal) {
    child.setParent(parent);
    child.setG(parent.g() + child.costFrom(parent));
    child.generateHeuristic(goal);
  }

  private void propagatePathImprovement(Node parent) {
    for (Node child : parent.getChildren()) {
      if (child.costFrom(parent) < child.g()) {
        child.setParent(parent);
        child.setG(parent.g() + child.costFrom(parent));
        propagatePathImprovement(child);
      }
    }
  }
}
