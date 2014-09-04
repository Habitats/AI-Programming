package algorithms.a_star;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 24.08.2014.
 */
public abstract class AStarNode implements Comparable<AStarNode> {

  private int h;
  private int g;
  private AStarNode parent;
  private List<AStarNode> children;
  private List<AStarNode> successors;
  private String state;
  private Integer count = null;
  private boolean closed;

  public AStarNode() {
    closed = false;
    children = new ArrayList<AStarNode>();
  }

  protected void setState(String state) {
    this.state = state;
  }

  public boolean hasParent() {
    return parent != null;
  }

  public AStarNode setParent(AStarNode parent) {
    this.parent = parent;
    setG(parent.g() + costFrom(parent));
    return this;
  }

  protected void addChild(AStarNode child) {
    children.add(child);
  }

  public String getState() {
    if (state == null) {
      generateState();
    }
    return state;
  }

  public AStarNode getParent() {
    return parent;
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
    return children;
  }

  public void setSuccsessors(List<AStarNode> successors) {
    this.successors = successors;
  }

  public List<AStarNode> getSuccessors() {
    if (successors == null) {
      generateSuccessors();
    }
    return successors;
  }

  public int getPathLength() {
    AStarNode node = this;
    int count = 0;
    while (node.hasParent()) {
      count++;
      node = node.getParent();
    }
    return count;
  }

  public AStarNode setG(int g) {
    this.g = g;
    return this;
  }

  public int f() {
    if (count == null) {
      return h() + g();
    } else {
      return count;
    }
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

  protected abstract int costFrom(AStarNode parent);

  protected abstract void generateSuccessors();

  protected abstract void generateState();

  protected abstract void generateHeuristic(AStarNode goal);

  public abstract void visualize();

  public abstract void devisualize();

  @Override
  public int compareTo(AStarNode o) {
    return this.f() - o.f();
  }

  @Override
  public String toString() {
    return ""//
           + "H: " + h() //
           + "  - G: " + g() //
           + " - F: " + f() //
           + " - Closed: " + closed //
           + " - State: " + getState();
  }

  public String toStringShort() {
//    return String.format("H: %d - G: %d - F: %d", h(), g(), f());
    return ""//
           + "H: " + h() //
           + " G: " + g() //
           + " - F: " + f()  //
//           + " C: " + isClosed() //
        ;
  }

  public boolean isSolution() {
    return h == 0;
  }

  public boolean hasBetter(AStarNode parent) {
    return parent.g() + costFrom(parent) < g();
  }
}
