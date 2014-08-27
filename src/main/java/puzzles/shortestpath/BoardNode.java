package puzzles.shortestpath;

import java.util.ArrayList;
import java.util.List;

import algorithms.a_star.AStarNode;
import ai.models.Board;
import ai.models.Tile;

/**
 * Created by Patrick on 24.08.2014.
 */
public class BoardNode extends AStarNode {

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
  public int costFrom(AStarNode parent) {
    return 30;
  }

  @Override
  protected void generateChildren() {
    List<AStarNode> children = new ArrayList<AStarNode>();
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
  public void generateHeuristic(AStarNode goal) {
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
    AStarNode node = this;
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
    for (AStarNode node : getChildren()) {
      Tile tile = ((BoardNode) node).getTile();
      tile.setState(Tile.State.CHILDREN);
      tile.setText(toStringShort());
      board.set(tile);
    }
    board.notifyDataChanged();
  }
}
