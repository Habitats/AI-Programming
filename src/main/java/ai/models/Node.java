package ai.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 08.09.2014.
 */
public abstract class Node<N extends Node> implements Comparable<N> {

  private List<N> children;
  private List<N> parents;

  public boolean hasParent() {
    return getParents().size() != 0;
  }

  public List<N> getParents() {
    if (parents == null) {
      parents = new ArrayList<>();
    }
    return parents;
  }

  public Node setParent(N parent) {
    parents = new ArrayList<>();
    parents.add(parent);
    return this;
  }

  public Node addParent(N parent) {
    getParents().add(parent);
    return this;
  }

  public void addChild(N child) {
    getChildren().add(child);
    child.<N>addParent(this);
  }

  public void remove() {
    for (N child : getChildren()) {
      child.<N>removeParent(this);
    }
  }

  public void removeParent(N node) {
    parents.remove(node);
  }

  public List<N> getChildren() {
    if (children == null) {
      children = new ArrayList<>();
    }
    return children;
  }

  public abstract String getId();
}
