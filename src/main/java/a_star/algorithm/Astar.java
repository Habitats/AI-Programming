package a_star.algorithm;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import a_star.node.Node;
import aiprog.Log;
import shortestpath.AstarCallback;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Astar implements Runnable {


  private enum Status {
    RUNNING, PAUSED, FINISHED, NO_SOLUTION;
  }

  private static final String TAG = Astar.class.getSimpleName();
  private final Node start;
  private final Node goal;
  private final AstarCallback callback;
  private Queue<Node> opened;
  private int count;
  private Map<String, Node> generated;
  private boolean dfs = false;
  private boolean bfs = false;
  private DateTime startTime;
  private Status status;

  public Astar(Node start, Node goal, AstarCallback callback) {
    this.start = start;
    this.goal = goal;
    this.callback = callback;
    setStatus(Status.PAUSED);
  }

  private Node search(Node start, Node goal) {
    startTime = new DateTime();
    start.generateHeuristic(goal);
    setStatus(Status.RUNNING);

    opened = new PriorityQueue<Node>();
    opened.add(start);

    // using a hash of all generated id's for faster lookup
    generated = new HashMap<String, Node>();

    // this is used for BFS/DFS
    count = 0;

    Node current;
    while ((current = opened.poll()) != null) {

      visualizeAndWait(current);
      Log.v(TAG, current);

      current.setClosed();

      if (current.isSolution()) {
        return current;
      }

      for (Node child : current.getChildren()) {
        child.addParent(current);
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

  private void visualize(Node node) {
    node.visualize();
  }

  private synchronized void visualizeAndWait(Node node) {
    node.visualize();
    try {
      Log.v(TAG, "waiting...");
      setStatus(Status.PAUSED);
      wait();
      Log.v(TAG, "continuing!");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void attachAndEvaluate(Node child, Node parent, Node goal) {
    child.addParent(parent);
    child.setG(parent.g() + child.costFrom(parent));
    child.generateHeuristic(goal);
  }

  private void propagatePathImprovement(Node parent) {
    for (Node child : parent.getChildren()) {
      if (child.costFrom(parent) < child.g()) {
        child.addParent(parent);
        child.setG(parent.g() + child.costFrom(parent));
        propagatePathImprovement(child);
      }
    }
  }


  @Override
  public void run() {
    Node best = search(start, goal);
    if (best == null) {
      callback.error();
      setStatus(Status.NO_SOLUTION);
    } else {
      callback.finished(best);
      setStatus(Status.FINISHED);
    }
  }


  public void setStatus(Status status) {
    this.status = status;
    Log.s(TAG, toString());
  }

  public Status getStatus() {
    return status;
  }

  private String getElapsedTime() {
    return new Interval(startTime, DateTime.now()).toDuration().toString();
  }

  @Override
  public String toString() {
    return "A* search -- Status: " + getStatus() //
           + " - Start: " + start  //
           + " - Goal: " + goal  //
           + " - Elapsed time: " + getElapsedTime();
  }
}
