package algorithms.csp;

import java.util.Queue;

/**
 * Created by Patrick on 04.09.2014.
 */
public class GAC implements Runnable {

  @Override
  public void run() {
    Queue<ReviseRequest> queue = initialize();
    domainFilter(queue);
  }

  private void domainFilter(Queue<ReviseRequest> queue) {
    boolean todoRevise;
    while (todoRevise = queue.poll() != null) {
      revise(todoRevise.getXStar, constraint(i));
      if (todoRevise.isReduced()) {
        queue.add()
      }
    }
  }

  private ReviseQueue<ReviseRequest> initialize() {
    ReviseQueue<ReviseRequest> queue = new ReviseQueue<>();
    Constraint constraints = getConstraints();
    for (Constraint c : constraints) {
      for (Variable x : c.getVariables()) {
        if (c.contains(x)) {
          queue.add(new ReviseRequest(x, c));
        }
      }
    }
    return queue;
  }

  private Constraint getConstraints() {
    return null;
  }
}
