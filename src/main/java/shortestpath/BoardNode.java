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

  private static final String TAG = BoardNode.class.getSimpleName();
  private Tile tile;
  private Board board;

  public BoardNode(Tile tile, Board board) {
    super();
    this.tile = tile;
    this.board = board;
  }

  public Tile getTile() {
    return tile;
  }

  @Override
  public int costFrom(Node parent) {
    return 50;
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
//        disallow diagonal moves
        if (tile.x != x && tile.y != y) {
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
    return (int) Math.round(h * 100);
  }

  @Override
  protected void generateState() {
    String id = "" + getTile().x + getTile().y;
//    if (hasParent()) {
//      id += getParent().getState();
//    }
    setState(id);
  }

  @Override
  public synchronized void visualize() {
    if (isSolution()) {
      drawPath();
    } else {
      drawChildren();
    }
  }

  private void drawPath() {
    Node node = this;
    while (node.hasParent()) {
      Tile tile = ((BoardNode) node).getTile();
      tile.setState(Tile.State.PATH);
      tile.setText(toStringShort());
      board.set(tile);
      node = node.getParent();
    }
    board.notifyDataChanged();
  }

  private void drawChildren() {
    for (Node node : getChildren()) {
      Tile tile = ((BoardNode) node).getTile();
      tile.setState(Tile.State.CHILDREN);
      tile.setText(toStringShort());
      board.set(tile);
    }
    board.notifyDataChanged();
  }
}
