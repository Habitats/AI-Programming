package puzzles.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ai.models.grid.Board;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarNode;
import puzzles.shortestpath.gui.ShortestPathGui;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AStarBoardNode extends AStarNode {

  private static final String TAG = AStarBoardNode.class.getSimpleName();
  private AStarTile tile;
  private Board<AStarTile> board;
  private int accuracyMultiplier = 10;

  public AStarBoardNode(AStarTile tile, Board<AStarTile> board) {
    super();
    this.tile = tile;
    this.board = board;
  }

  public AStarTile getTile() {
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
        // disallow diagonal moves
        if (tile.x != x && tile.y != y) {
          continue;
        }
        // check if coordinates for the specified successor are valid on the board.
        // ie. if the coordinates is an obstacle, or are out of bounds, it will return false
        if (board.hasAvailableTile(x, y)) {
          // finally, generate a new BoardNode with the new coordinates
          successors.add(new AStarBoardNode(board.get(x, y), board));
        }
      }
    }
    if (AStar.SHUFFLE_CHILDREN) {
      Collections.shuffle(successors);
    }
    setSuccsessors(successors);
  }

  @Override
  public void generateHeuristic() {
    int distance = euclideanDistance(this.getTile(), board.getGoal());
//    int distance = manhattanDistance(this, (BoardNode) goal);
    setHeuristic(distance);
  }

  private void getGoal() {

  }

  private int euclideanDistance(AStarTile start, AStarTile goal) {
    double k1 = Math.pow(goal.y - start.y, 2);
    double k2 = Math.pow(goal.x - start.x, 2);
    double h = Math.sqrt(k1 + k2);
    return (int) Math.round(h * accuracyMultiplier);
  }

  private int manhattanDistance(AStarBoardNode start, AStarBoardNode goal) {
    int k1 = Math.abs(goal.getTile().y - start.getTile().y);
    int k2 = Math.abs(goal.getTile().x - start.getTile().x);
    return (k1 + k2) * accuracyMultiplier;
  }

  @Override
  protected void generateState() {
    String id = "x:" + getTile().x + "y:" + getTile().y;
    setState(id);
  }

  @Override
  public synchronized void visualize() {
    if (isSolution()) {
      drawPath();
//      board.set(this.getTile());
    } else {
      if (ShortestPathGui.DRAW_CHILDREN) {
        drawChildren();
      }
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

  @Override
  public void onPostSearch() {

  }

  private void erasePath() {
    drawPath(getTile().getPreviousState());
  }

  private void drawPath() {
    drawPath(AStarTile.State.PATH);
  }

  private void drawPath(AStarTile.State state) {
    synchronized (board) {
      AStarNode node = this;
      while (node.hasParent()) {
        AStarTile tile = ((AStarBoardNode) node).getTile();
        tile.setState(state);
        tile.setText(node.toStringShort());
        board.set(tile);
        node = node.getParent();
      }
      board.notifyDataChanged();
    }
  }

  private void drawChildren() {
    for (AStarNode node : getSuccessors()) {
      AStarTile tile = ((AStarBoardNode) node).getTile();
      tile.setState(AStarTile.State.CHILDREN);
//      tile.setText(node.toStringShort());
      board.set(tile);
    }
    board.notifyDataChanged();
  }
}
