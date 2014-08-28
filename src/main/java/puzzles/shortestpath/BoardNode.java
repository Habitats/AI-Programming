package puzzles.shortestpath;

import java.util.ArrayList;
import java.util.List;

import ai.models.Board;
import ai.models.Tile;
import algorithms.a_star.AStarNode;

/**
 * Created by Patrick on 24.08.2014.
 */
public class BoardNode extends AStarNode {

  private static final String TAG = BoardNode.class.getSimpleName();
  private Tile tile;
  private Board board;
  private int accuracyMultiplier = 10;

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
    return (int) (.5 * accuracyMultiplier);
  }

  @Override
  protected void generateSuccessors() {
    List<AStarNode> successors = new ArrayList<AStarNode>();
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
          successors.add(new BoardNode(board.get(x, y), board));
        }
      }
    }

    setSuccsessors(successors);

  }

  @Override
  public void generateHeuristic(AStarNode goal) {
    setHeuristic(distance(this, (BoardNode) goal));
  }

  private int distance(BoardNode start, BoardNode goal) {
    int k1 = goal.getTile().y - start.getTile().y;
    int k2 = goal.getTile().x - start.getTile().x;
    double h = Math.sqrt(Math.pow(k1, 2) + Math.pow(k2, 2));
    return (int) Math.round(h * accuracyMultiplier);
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
//      board.set(this.getTile());
    } else {
      drawChildren();
      drawPath();
      board.set(this.getTile());
    }
    board.setStart(board.getStart());
    board.setGoal(board.getGoal());
  }

  @Override
  public void devisualize() {
    erasePath();
  }

  private void erasePath() {
    drawPath(getTile().getPreviousState());
  }

  private void drawPath() {
    drawPath(Tile.State.PATH);
  }

  private void drawPath(Tile.State state) {
    AStarNode node = this;
    while (node.hasParent()) {
      Tile tile = ((BoardNode) node).getTile();
      tile.setState(state);
      tile.setText(node.toStringShort());
      board.set(tile);
      node = node.getParent();
    }
    board.notifyDataChanged();
  }

  private void drawChildren() {
    for (AStarNode node : getChildren()) {
      Tile tile = ((BoardNode) node).getTile();
      tile.setState(Tile.State.CHILDREN);
//      tile.setText(node.toStringShort());
      board.set(tile);
    }
    board.notifyDataChanged();
  }
}
