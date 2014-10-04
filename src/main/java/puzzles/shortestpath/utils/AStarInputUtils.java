package puzzles.shortestpath.utils;

import java.util.ArrayList;
import java.util.List;

import ai.models.grid.Board;
import puzzles.shortestpath.AStarColorTile;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AStarInputUtils {

  private static final String TAG = AStarInputUtils.class.getSimpleName();
  private ArrayList<List<Integer>> inputList;
  //  Input for A*
  //  Index Dimensions  Start   Goal      Barriers
  //  0     (10, 10)    (0, 0)  (9, 9)    (2, 3, 5, 5) (8, 8, 2, 1)
  //  1     (20, 20)    (19, 3) (2, 18)   (5, 5, 10, 10) (1, 2, 4, 1)
  //  2     (20, 20)    (0, 0)  (19, 19)  (17, 10, 2, 1) (14, 4, 5, 2) (3, 16, 10, 2) (13, 7, 5, 3) (15, 15, 3, 3)
  //  3     (10, 10)    (0, 0)  (9, 5)    (3, 0, 2, 7) (6, 0, 4, 4) (6, 6, 2, 4)
  //  4     (10, 10)    (0, 0)  (9, 9)    (3, 0, 2, 7) (6, 0, 4, 4) (6, 6, 2, 4)
  //  5     (20, 20)    (0, 0)  (19, 13)  (4, 0, 4, 16) (12, 4, 2, 16) (16, 8, 4, 4)

  private AStarInputUtils(String input) {
    setInputList(input);
  }

  public static AStarInputUtils build(String input) {
    return new AStarInputUtils(input);
  }

  public AStarColorTile getStart() {
    List<Integer> params = getInputList().get(1);
    AStarColorTile tile = new AStarColorTile(params.get(0), params.get(1), AStarColorTile.State.START);
    return tile;
  }

  public AStarColorTile getGoal() {
    List<Integer> params = getInputList().get(2);
    AStarColorTile tile = new AStarColorTile(params.get(0), params.get(1), AStarColorTile.State.GOAL);
    return tile;
  }

  public Board getBoard() {
    Integer boardWidth = inputList.get(0).get(0);
    Integer boardHeight = inputList.get(0).get(1);
    Board<AStarColorTile> board = generateCleanBoard(boardWidth, boardHeight);
    for (int i = 3; i < inputList.size(); i++) {
      int x = inputList.get(i).get(0);
      int y = inputList.get(i).get(1);
      int width = inputList.get(i).get(2);
      int height = inputList.get(i).get(3);
      for (int w = 0; w < width; w++) {
        for (int h = 0; h < height; h++) {
          AStarColorTile astarTile = new AStarColorTile(x + w, y + h, AStarColorTile.State.OBSTICLE);
          board.set(astarTile);
        }
      }
    }
    board.setStart(getStart());
    board.setGoal(getGoal());
    board.notifyDataChanged();
    return board;
  }

  private Board<AStarColorTile> generateCleanBoard(int width, int height) {
    Board<AStarColorTile> board = new Board<>(width, height);
    board.clear();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        AStarColorTile tile = new AStarColorTile(x, y, AStarColorTile.State.EMPTY);
        board.set(tile);
      }
    }
    board.notifyDataChanged();
    return board;
  }

  private ArrayList<List<Integer>> getInputList() {
    return inputList;
  }

  private void setInputList(String input) {
    inputList = new ArrayList<>();
    for (String s : input.split("\\(")) {
      String stripped = s.split("\\)")[0].trim();
      List<Integer> ints = new ArrayList<>();
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
