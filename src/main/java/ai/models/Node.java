package ai.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 08.09.2014.
 */
public abstract class Node<T extends Node> implements Comparable<T> {

  private List<T> children;
  private List<T> parents;

  public boolean hasParent() {
    return getParents().size() != 0;
  }

  public List<T> getParents() {
    if (parents == null) {
      parents = new ArrayList<>();
    }
    return parents;
  }

  public Node setParent(T parent) {
    parents = new ArrayList<>();
    parents.add(parent);
    return this;
  }

  public Node addParent(T parent) {
    getParents().add(parent);
    return this;
  }

  public void addChild(T child) {
    getChildren().add(child);
    child.addParent(this);
  }

  public void remove() {
    for (Node child : getChildren()) {
      child.removeParent(this);
    }
  }

  public void removeParent(T node) {
    parents.remove(node);
  }

  public List<T> getChildren() {
    if (children == null) {
      children = new ArrayList<>();
    }
    return children;
  }

  public abstract String getId();
}
