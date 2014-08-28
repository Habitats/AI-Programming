package algorithms.a_star;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import ai.Log;

/**
 * Created by Patrick on 23.08.2014.
 */
public class AStar implements Runnable {


  private enum Status {
    RUNNING, PAUSED, FINISHED, NO_SOLUTION;
  }

  private static final String TAG = AStar.class.getSimpleName();
  private final AStarNode start;
  private final AStarNode goal;
  private final AStarCallback callback;
  private Queue<AStarNode> opened;
  private int count;
  private Map<String, AStarNode> generated;
  private boolean dfs = false;
  private boolean bfs = false;
  private DateTime startTime;
  private Status status;

  // step time in ms
  private int stepTime = 100;


  public AStar(AStarNode start, AStarNode goal, AStarCallback callback) {
    this.start = start;
    this.goal = goal;
    this.callback = callback;
    setStatus(Status.PAUSED);
  }


  private AStarNode search(AStarNode start, AStarNode goal) {
    startTime = new DateTime();
    start.generateHeuristic(goal);
    setStatus(Status.RUNNING);

    opened = new PriorityQueue<AStarNode>();
    opened.add(start);

    // using a hash of all generated id's for faster lookup
    generated = new HashMap<String, AStarNode>();

    // this is used for BFS/DFS
    count = 0;

    AStarNode current;
    while ((current = opened.poll()) != null) {

      visualizeAndWait(current);
      Log.v(TAG, current);

      current.setClosed();

      if (current.isSolution()) {
        return current;
      }

      for (AStarNode succsessor : current.getSuccessors()) {
        // if child has already been generated, use that child
        if (generated.containsKey(succsessor.getState())) {
          succsessor = generated.get(succsessor.getState());
        }
        current.addChild(succsessor);

        // if child is new
        if (!generated.containsKey(succsessor.getState())) {
          generated.put(succsessor.getState(), succsessor);
          attachAndEvaluate(succsessor, current, goal);
          succsessor.setCount(count);

//          if (dfs) {
//            count -= 1;
//          } else if (bfs) {
//            count += 1;
//          }

          opened.add(succsessor);
        }

        // else if child has previously been generated, see if it is better
        else if (succsessor.hasBetter(current)) {
          attachAndEvaluate(succsessor, current, goal);
          if (succsessor.isClosed()) {
            propagatePathImprovement(succsessor);
          }
        }
      }
    }

    return null;
  }

  private synchronized void visualizeAndWait(AStarNode node) {
    node.visualize();
    try {
      Log.v(TAG, "waiting...");
      setStatus(Status.PAUSED);
      wait(stepTime);
      node.devisualize();
      Log.v(TAG, "continuing!");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void attachAndEvaluate(AStarNode child, AStarNode parent, AStarNode goal) {
    child.setParent(parent);
    child.generateHeuristic(goal);
  }

  private void propagatePathImprovement(AStarNode parent) {
    Log.v(TAG, "Propagate path improvement: " + parent);
    for (AStarNode child : parent.getChildren()) {
      if (child.hasBetter(parent)) {
        Log.v(TAG, "path length: " + child.getPathLength());
        child.setParent(parent);
        Log.v(TAG, "new length: " + child.getPathLength() + "\n--------------");
        propagatePathImprovement(child);
      }
    }
  }


  @Override
  public void run() {
    AStarNode best = search(start, goal);
    if (best == null) {
      callback.error();
      setStatus(Status.NO_SOLUTION);
    } else {
      callback.finished(best);
      setStatus(Status.FINISHED);
    }
  }


  public void setStepTime(int stepTime) {
    this.stepTime = stepTime;
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
