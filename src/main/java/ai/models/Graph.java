package ai.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Patrick on 08.09.2014.
 */
public class Graph<T extends Node> extends AIAdapter implements Iterable<T> {

  private List<T> nodes;
  private int width;
  private int height;

  public Graph<T> addNode(T node) {
    getNodes().add(node);
    if (node instanceof ColorNode) {
      ColorNode cNode = (ColorNode) node;
      if (cNode.getX() > width) {
        width = cNode.getX();
      }
      if (cNode.getY() > height) {
        height = cNode.getY();
      }
    }
    return this;
  }

  public Graph<T> addEdge(Node one, Node two) {
    one.addChild(two);
    two.addChild(one);
    return this;
  }

  public int getWidth() {
    return width + 1;
  }

  public int getHeight() {
    return height + 1;
  }

  public T get(int i) {
    return getNodes().get(i);
  }

  public void addEdge(int vertexOne, int vertexTwo) {
    getNodes().get(vertexOne).addChild(getNodes().get(vertexTwo));
    getNodes().get(vertexTwo).addChild(getNodes().get(vertexOne));
  }

  public int getSize() {
    return getNodes().size();
  }

  public List<T> getNodes() {
    if (nodes == null) {
      nodes = new ArrayList<>();
    }
    return nodes;
  }

  @Override
  public Iterator<T> iterator() {
    return nodes.iterator();
  }
}
