package a_star.node;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Patrick on 24.08.2014.
 */
public abstract class Node implements Comparable<Node> {

  private int h;
  private int g;
  private Node goal;
  private Queue<Node> parents;
  private List<Node> children;
  private String state;
  private int count;
  private boolean closed;

  public Node() {
    closed = false;
    parents = new PriorityQueue<Node>();
  }

  protected void setChildren(List<Node> children) {
    this.children = children;
  }

  protected void setState(String state) {
    this.state = state;
  }

  public boolean hasParent() {
    return parents.size() > 0;
  }

  public Node addParent(Node parent) {
    parents.add(parent);
    return this;
  }

  public String getState() {
    if (state == null) {
      generateState();
    }
    return state;
  }

  public Node getParent() {
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

  public List<Node> getChildren() {
    if (children == null) {
      generateChildren();
    }
    return children;
  }

  public Node setG(int g) {
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

  public abstract int costFrom(Node parent);

  protected abstract void generateChildren();

  protected abstract void generateState();

  public abstract void generateHeuristic(Node goal);

  public abstract void visualize();

  @Override
  public int compareTo(Node o) {
    return this.f() - o.f();
  }

  @Override
  public String toString() {
    return String.format("H: %d - G: %d - F: %d - State: %s", h(), g(), f(), getState());
  }

  public boolean isSolution() {
    return h == 0;
  }
}
