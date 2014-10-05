package ai.models.graph;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ai.models.AIAdapter;
import ai.models.Node;

/**
 * Created by Patrick on 08.09.2014.
 */
public class Graph<N extends Node> extends AIAdapter implements Iterable<N> {

  private List<N> items;

  public Graph<N> addNode(N node) {
    getItems().add(node);
    return this;
  }

  public Graph<N> addEdge(N one, N two) {
    one.addChild(two);
    two.addChild(one);
    return this;
  }

  @Override
  public N getItem(int index) {
    return items.get(index);
  }

  public N get(int i) {
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
  public List<N> getItems() {
    if (items == null) {
      items = new ArrayList<>();
    }
    return items;
  }

  @Override
  public boolean isLegalPosition(Node tile) {
    throw new NotImplementedException();
  }

  @Override
  public Iterator<N> iterator() {
    return items.iterator();
  }
}
