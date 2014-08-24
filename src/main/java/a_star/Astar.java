package a_star;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Astar {

  private Queue<Node> opened;
  private int count;
  private Map<String, Node> generated;
  private boolean dfs = false;
  private boolean bfs = false;

  public Node search(Node start, Node goal) {
    start.generateHeuristic(goal);

    opened = new PriorityQueue<Node>();
    opened.add(start);

//    # using a hash of all generated id's for faster lookup
    generated = new HashMap<String, Node>();

    // this is used for BFS/DFS
    count = 0;

    while (opened.size() > 0) {
      Node current = opened.poll();

      current.setStatus(Node.Status.CLOSED);

      if (current.h() == 0) {
        return current;
      }

      current.generateChildren();

      for (Node child : current.getChildren()) {
        if (generated.containsKey(child.getState())) {
          child = generated.get(child);
        }

        if (!generated.containsKey(child.getState())) {
          child.generateHeuristic(goal);
          generated.put(child.getState(), child);
          attachAndEvaluate(child, current);
          child.setCount(count);

          if (dfs) {
            count -= 1;
          } else if (bfs) {
            count += 1;
          }

          opened.add(child);
        } else if (child.g() > child.tentativeG(current)) {
          attachAndEvaluate(child, current);
          if (child.getStatus() == Node.Status.CLOSED) {
            propagateG(child);
          }
        }
      }
    }

    return null;
  }

  private void attachAndEvaluate(Node child, Node parent) {
    child.setParent(parent);
    child.setG(child.tentativeG(parent));
  }

  private void propagateG(Node parent) {
    for (Node child : parent.getChildren()) {
      if (child.tentativeG(parent) < child.g()) {
        child.setParent(parent);
        child.setG(child.tentativeG(parent));
        propagateG(child);
      }
    }
  }
}
