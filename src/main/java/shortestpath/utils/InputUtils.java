package shortestpath.utils;

import java.util.ArrayList;
import java.util.List;

import shortestpath.models.Board;
import shortestpath.models.Tile;

/**
 * Created by Patrick on 24.08.2014.
 */
public class InputUtils {

  private static final String TAG = InputUtils.class.getSimpleName();
  private ArrayList<List<Integer>> inputList;
  //  Input for A*
  //  Index Dimensions  Start   Goal      Barriers
  //  0     (10, 10)    (0, 0)  (9, 9)    (2, 3, 5, 5) (8, 8, 2, 1)
  //  1     (20, 20)    (19, 3) (2, 18)   (5, 5, 10, 10) (1, 2, 4, 1)
  //  2     (20, 20)    (0, 0)  (19, 19)  (17, 10, 2, 1) (14, 4, 5, 2) (3, 16, 10, 2) (13, 7, 5, 3) (15, 15, 3, 3)
  //  3     (10, 10)    (0, 0)  (9, 5)    (3, 0, 2, 7) (6, 0, 4, 4) (6, 6, 2, 4)
  //  4     (10, 10)    (0, 0)  (9, 9)    (3, 0, 2, 7) (6, 0, 4, 4) (6, 6, 2, 4)
  //  5     (20, 20)    (0, 0)  (19, 13)  (4, 0, 4, 16) (12, 4, 2, 16) (16, 8, 4, 4)

  private InputUtils(String input) {
    setInputList(input);
  }

  public static InputUtils build(String input) {
    return new InputUtils(input);
  }

  public Tile getStart() {
    List<Integer> params = getInputList().get(1);
    Tile tile = new Tile(params.get(0), params.get(1), Tile.State.START);
    return tile;
  }

  public Tile getGoal() {
    List<Integer> params = getInputList().get(2);
    Tile tile = new Tile(params.get(0), params.get(1), Tile.State.GOAL);
    return tile;
  }

  public Board getBoard() {
    Board board = new Board(inputList.get(0).get(0), inputList.get(0).get(1));
    board.clear();
    for (int i = 3; i < inputList.size(); i++) {
      int x = inputList.get(i).get(0);
      int y = inputList.get(i).get(1);
      int width = inputList.get(i).get(2);
      int height = inputList.get(i).get(3);
      board.set(x, y, width, height, Tile.State.OBSTICLE);
    }
    board.setStart(getStart());
    board.setGoal(getGoal());
    return board;
  }

  private ArrayList<List<Integer>> getInputList() {
    return inputList;
  }

  private void setInputList(String input) {
    inputList = new ArrayList<List<Integer>>();
    for (String s : input.split("\\(")) {
      String stripped = s.split("\\)")[0].trim();
      List<Integer> ints = new ArrayList<Integer>();
      if (stripped.isEmpty()) {
        continue;
      }
      for (String i : stripped.split(",")) {
        i = i.trim();
        if (i.isEmpty()) {
          continue;
        }
        ints.add(Integer.parseInt(i));
      }
      inputList.add(ints);
    }
  }
}
