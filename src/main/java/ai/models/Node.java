package ai.models;

import java.util.ArrayList;
import java.util.List;

import algorithms.a_star.AStarNode;

/**
 * Created by Patrick on 08.09.2014.
 */
public abstract class Node implements Comparable<AStarNode> {

  private List<Node> children;
  private List<Node> parents;

  public boolean hasParent() {
    return getParents().size() != 0;
  }

  public List<Node> getParents() {
    if (parents == null) {
      parents = new ArrayList<>();
    }
    return parents;
  }

  public Node setParent(Node parent) {
    parents = new ArrayList<>();
    parents.add(parent);
    return this;
  }

  public Node addParent(Node parent) {
    getParents().add(parent);
    return this;
  }

  public void addChild(Node child) {
    getChildren().add(child);
    child.addParent(this);
  }

  public void remove() {
    for (Node child : getChildren()) {
      child.removeParent(this);
    }
  }

  public void removeParent(Node node) {
    parents.remove(node);
  }

  public List<Node> getChildren() {
    if (children == null) {
      children = new ArrayList<>();
    }
    return children;
  }
}
