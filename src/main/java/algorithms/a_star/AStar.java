package algorithms.a_star;

import org.joda.time.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.Log;

/**
 * Created by Patrick on 23.08.2014.
 */
public class AStar implements Runnable {


  public static boolean MANUAL_STEP = false;
  public static boolean SHUFFLE_CHILDREN = true;
  private boolean terminate;

  public Traversal getTraversal() {
    return traversal;
  }

  public void runInBackground() {
    new Thread(this).start();
  }

  private enum Status {
    RUNNING, PAUSED, FINISHED, NO_SOLUTION
  }

  public enum Traversal {
    DEPTH_FIRST, BREATH_FIRST, BEST_FIRST
  }

  private static final String TAG = AStar.class.getSimpleName();
  private final AStarNode start;
  private final Traversal traversal;
  private final AStarCallback callback;
  private final Map<String, AStarNode> generated;
  private long startTime;
  private long endTime;
  private Status status;

  // step time in ms
  private int stepTime = 1;


  public AStar(AStarNode start, AStarCallback callback) {
    this(start, Traversal.BEST_FIRST, callback);
  }

  public AStar(AStarNode start, Traversal traversal, AStarCallback callback) {
    this.start = start;
    this.traversal = traversal;
    this.callback = callback;
    // using a hash of all generated id's for faster lookup
    generated = new HashMap<>();
    setStatus(Status.PAUSED);
  }

  private AStarNode search(AStarNode start) {
    startTime = System.currentTimeMillis();
    start.generateHeuristic();
    setStatus(Status.RUNNING);

    Queue<AStarNode> opened = new PriorityQueue<>();
    opened.add(start);

    // this is used for BFS/DFS
    int count = 0;

    AStarNode current = null;
    while (opened.size() > 0) {
      current = opened.poll();
      Log.i(TAG, current.toStringShort() + " - " + toString());

      visualizeAndWait(current);
//      Log.v(TAG, toString());

      current.setClosed();

      if (current.isSolution() || shouldTerminate()) {
        setStatus(Status.FINISHED);
        return current;
      }

      for (AStarNode succsessor : current.getSuccessors()) {
        // if child has already been generated, use that child
        if (generated.containsKey(succsessor.getId())) {
          succsessor = generated.get(succsessor.getId());
        }
        current.addChild(succsessor);

        // if child is new
        if (!generated.containsKey(succsessor.getId())) {
          generated.put(succsessor.getId(), succsessor);
          attachAndEvaluate(succsessor, current);

          if (traversal == Traversal.DEPTH_FIRST) {
            succsessor.setCount(count--);
          } else if (traversal == Traversal.BREATH_FIRST) {
            succsessor.setCount(count++);
          }

          opened.add(succsessor);
        }



        // else if child has previously been generated, see if it is better
        else if (succsessor.hasBetter(current)) {
          attachAndEvaluate(succsessor, current);
          if (succsessor.isClosed()) {
            propagatePathImprovement(succsessor);
          }
        }
      }
    }

    endTime = System.currentTimeMillis() - startTime;

    setStatus(Status.NO_SOLUTION);
    return current;
  }

  private boolean shouldTerminate() {
    return terminate;
  }

  private synchronized void visualizeAndWait(AStarNode node) {
    node.visualize();
    try {
//      Log.v(TAG, "waiting...");
      setStatus(Status.PAUSED);
      if (MANUAL_STEP) {
        wait();
      } else {
        wait(stepTime);
      }
      node.devisualize();
//      Log.v(TAG, "continuing!");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void attachAndEvaluate(AStarNode child, AStarNode parent) {
    child.setParent(parent);
    child.generateHeuristic();
  }

  private void propagatePathImprovement(AStarNode parent) {
    Log.v(TAG, "Propagate path improvement: " + parent);
    for (int i = 0; i < parent.getChildren().size(); i++) {
      AStarNode child = parent.getChildren().get(i);
      if (child.hasBetter(parent)) {
        Log.v(TAG, "path length: " + child.getPathLength());
        child.setParent(parent);
        Log.v(TAG, "new length: " + child.getPathLength() + "\n--------------");
        propagatePathImprovement(child);
      }
    }
  }

  public void terminate() {
    terminate = true;
    Log.i(TAG, "terminating A* search ...");
  }

  @Override
  public void run() {
    AStarNode best = search(start);
    Log.i(TAG, this);
    best.onPostSearch();
    if (status == Status.NO_SOLUTION) {
      callback.error();
    } else if (status == Status.FINISHED) {
      callback.finished(best, this);
      Log.i(TAG, getTraversal() + " - generated nodes: " + getGeneratedSize() + " - solution lenght: " + best
          .getPathLength());
    }
  }


  public void setStepTime(int stepTime) {
    this.stepTime = stepTime;
  }


  public void setStatus(Status status) {
    this.status = status;
//    Log.s(TAG, status.name());
  }

  public Status getStatus() {
    return status;
  }

  private String getElapsedTime() {
    return new Duration(System.currentTimeMillis() - startTime).toString().substring(2);
  }

  @Override
  public String toString() {
    return traversal + " search" //
           + " - Generated: " + getGeneratedSize() //
           + " - Closed: " + getClosedSize() //
           + " - Opened: " + getOpenedSize() //
           + " - Elapsed time: " + getElapsedTime();
  }

  private int getClosedSize() {
    int count = 0;
    for (AStarNode node : generated.values()) {
      count = node.isClosed() ? count + 1 : count;
    }
    return count;
  }

  private int getOpenedSize() {
    return getGeneratedSize() - getClosedSize();
  }

  public int getGeneratedSize() {
    return generated.size();
  }
}
