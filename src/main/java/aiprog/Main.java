package aiprog;

import a_star.Astar;
import a_star.gui.BoardGridBag;
import a_star.models.Board;
import a_star.models.Tile;

/**
 * Created by Patrick on 24.08.2014.
 */
public class Main {

  public static void main(String args[]) {
    Board board = new Board(6, 6);
    BoardGridBag frame = new BoardGridBag();
    frame.setAdapter(board);

    board.clear();
    board.set(0, 3, 1, 3, Tile.State.FILLED);
    board.set(3,2,2,2 , Tile.State.FILLED);
    board.set(2, 0, 4, 2, Tile.State.FILLED);
    board.set(2, 5, 2, 1, Tile.State.FILLED);
    board.set(1,0,1,2, Tile.State.OUTLINE);
    new Astar().run();
  }
}
