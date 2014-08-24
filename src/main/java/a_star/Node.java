package a_star;

import java.util.List;

/**
 * Created by Patrick on 24.08.2014.
 */
public abstract class Node implements Comparable<Node> {

  public Status getStatus() {
    return status;
  }

  public enum Status {
    OPENED, CLOSED
  }

  private int h;
  private int g;
  private Node goal;
  private Node parent;
  private List<Node> children;
  private Status status;
  private String state;
  private int count;

  public Node(String state) {
    this.state = state;
    status = Status.OPENED;
    generateChildren();
  }

  public boolean hasParent() {
    return parent != null;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Node setParent(Node parent) {
    this.parent = parent;
    return this;
  }

  public String getState() {
    return state;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public List<Node> getChildren() {
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

  protected abstract int tentativeG(Node parent);

  protected abstract void generateChildren();

  protected abstract void generateHeuristic(Node goal);

  protected abstract void generateState();

  @Override
  public int compareTo(Node o) {
    return this.f() - o.f();
  }

  @Override
  public String toString() {
    return String.format("H: %d - G: %d - F: %d", h(), g(), f());
  }
}
