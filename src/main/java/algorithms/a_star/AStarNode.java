package algorithms.a_star;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Patrick on 24.08.2014.
 */
public abstract class AStarNode implements Comparable<AStarNode> {

  private int h;
  private int g;
  private AStarNode goal;
  private Queue<AStarNode> parents;
  private List<AStarNode> children;
  private String state;
  private int count;
  private boolean closed;

  public AStarNode() {
    closed = false;
    parents = new PriorityQueue<AStarNode>();
  }

  protected void setChildren(List<AStarNode> children) {
    this.children = children;
  }

  protected void setState(String state) {
    this.state = state;
  }

  public boolean hasParent() {
    return parents.size() > 0;
  }

  public AStarNode addParent(AStarNode parent) {
    parents.add(parent);
    return this;
  }

  public String getState() {
    if (state == null) {
      generateState();
    }
    return state;
  }

  public AStarNode getParent() {
    return parents.peek();
  }

  public void setHeuristic(int h) {
    this.h = h;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public List<AStarNode> getChildren() {
    if (children == null) {
      generateChildren();
    }
    return children;
  }

  public AStarNode setG(int g) {
    this.g = g;
    return this;
  }

  public int f() {
    return h() + g();
  }

  public int g() {
    return g;
  }

  public int h() {
    return h;
  }

  public void setClosed() {
    closed = true;
  }

  public boolean isClosed() {
    return closed;
  }

  public abstract int costFrom(AStarNode parent);

  protected abstract void generateChildren();

  protected abstract void generateState();

  public abstract void generateHeuristic(AStarNode goal);

  public abstract void visualize();

  @Override
  public int compareTo(AStarNode o) {
    return this.f() - o.f();
  }

  @Override
  public String toString() {
    return String.format("H: %d - G: %d - F: %d - State: %s", h(), g(), f(), getState());
  }

  public String toStringShort(){
    return String.format("H: %d - G: %d - F: %d", h(), g(), f());
  }

  public boolean isSolution() {
    return h == 0;
  }
}
