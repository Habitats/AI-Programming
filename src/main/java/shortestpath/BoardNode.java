package shortestpath;

import java.util.ArrayList;
import java.util.List;

import a_star.node.Node;
import shortestpath.models.Board;
import shortestpath.models.Tile;

/**
 * Created by Patrick on 24.08.2014.
 */
public class BoardNode extends Node {

  private Tile tile;
  private Board board;

  public BoardNode(Tile tile, Board board) {
    this.tile = tile;
    this.board = board;
  }

  public Tile getTile() {
    return tile;
  }

  @Override
  public int costFrom(Node parent) {
    return 1;
  }

  @Override
  protected void generateChildren() {
    List<Node> children = new ArrayList<Node>();
    for (int x = tile.x - 1; x <= tile.x + 1; x++) {
      for (int y = tile.y - 1; y <= tile.y + 1; y++) {
        // do not add self to its own children
        if (x == tile.x && y == tile.y) {
          continue;
        }
        if (board.hasTile(x, y)) {
          children.add(new BoardNode(board.get(x, y), board));
        }
      }
    }
    setChildren(children);
  }

  @Override
  public void generateHeuristic(Node goal) {
    setHeuristic(distance(this, (BoardNode) goal));
  }

  private int distance(BoardNode start, BoardNode goal) {
    int k1 = goal.getTile().y - start.getTile().y;
    int k2 = goal.getTile().x - start.getTile().x;
    double h = Math.sqrt(Math.pow(k1, 2) + Math.pow(k2, 2));
    return (int) Math.round(h);
  }

  @Override
  protected void generateState() {
    String id = "" + getTile().x + getTile().y;
    if (hasParent()) {
      id += getParent().getState();
    }
    setState(id);
  }

  @Override
  public void visualize() {
    Node node = this;
    while (node.hasParent()) {
      Tile tile = ((BoardNode) node).getTile();
      tile.setState(Tile.State.PATH);
      board.set(tile);
      node = node.getParent();
    }
  }
}