package ai.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Patrick on 08.09.2014.
 */
public class Graph<T extends Node> extends AIAdapter implements Iterable<T> {

  private List<T> items;

  public Graph<T> addNode(T node) {
    getItems().add(node);
    return this;
  }

  public Graph<T> addEdge(Node one, Node two) {
    one.addChild(two);
    two.addChild(one);
    return this;
  }

  @Override
  public T getItem(int index) {
    return items.get(index);
  }

  public T get(int i) {
    return getItems().get(i);
  }

  public void addEdge(int vertexOne, int vertexTwo) {
    getItems().get(vertexOne).addChild(getItems().get(vertexTwo));
    getItems().get(vertexTwo).addChild(getItems().get(vertexOne));
  }

  @Override
  public int getSize() {
    return getItems().size();
  }

  @Override
  public List<T> getItems() {
    if (items == null) {
      items = new ArrayList<>();
    }
    return items;
  }

  @Override
  public Iterator<T> iterator() {
    return items.iterator();
  }
}
