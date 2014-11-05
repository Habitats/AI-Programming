package puzzles.game20480;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ai.Log;
import ai.gui.AICanvas;
import ai.gui.AICanvas.Direction;
import ai.models.grid.Board;
import algorithms.minimax.MiniMaxState;

import static puzzles.game20480.Game2048Tile.VALUE.v0;
import static puzzles.game20480.Game2048Tile.VALUE.v2;
import static puzzles.game20480.Game2048Tile.VALUE.v4;

/**
 * Created by Patrick on 29.10.2014.
 */
public class Game2048Board extends Board<Game2048Tile> implements MiniMaxState {

  private static final String TAG = Game2048Board.class.getSimpleName();
  private boolean didMove;
  private Direction lastMove;
  private MiniMaxState parent;
  private Integer score;
  private List<MiniMaxState> possibleNextStates;
  private int b;
  private int a;
  private List<MiniMaxState> children;

  public Game2048Board() {
    super(4, 4);

    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        Game2048Tile tile = new Game2048Tile(x, y);
        tile.setValue(v0);
        set(tile);
      }
    }
  }

  private List<MiniMaxState> opposingStates;

  public Direction getLastMove() {
    return lastMove;
  }

  public void move(Direction direction) {
    move(direction, true);
  }

  public boolean move(Direction direction, boolean shouldPlace) {
    didMove = false;
    lastMove = direction;
    switch (direction) {
      case UP:
        up();
        break;
      case RIGHT:
        right();
        break;
      case DOWN:
        down();
        break;
      case LEFT:
        left();
        break;
    }

    if (shouldPlace && didMove) {
      place();
      notifyDataChanged();
    }
    return didMove;
  }

  private void up() {
    for (int y = 2; y >= 0; y--) {
      for (int x = 0; x < 4; x++) {
        Game2048Tile tile = get(x, y);
        if (tile.isEmpty()) {
          continue;
        }
        int yi = y + 1;
        while (hasEmptyTile(x, yi)) {
          tile = get(x, yi - 1);
          move(tile, x, yi);
          tile = get(x, yi);
          yi++;
        }
        if (yi > 3) {
          continue;
        }
        Game2048Tile newTile = get(x, yi);
        combine(tile, newTile);
      }
    }
  }

  private List<Game2048Tile> getPieces() {
    List<Game2048Tile> pieces = new ArrayList<>();
    for (Game2048Tile tile : getItems()) {
      if (!tile.isEmpty()) {
        pieces.add(tile);
        tile.reset();
      }
    }
    return pieces;
  }

  private void right() {
    for (int x = 2; x >= 0; x--) {
      for (int y = 0; y < 4; y++) {
        Game2048Tile tile = get(x, y);
        if (tile.isEmpty()) {
          continue;
        }
        int xi = x + 1;
        while (hasEmptyTile(xi, y)) {
          tile = get(xi - 1, y);
          move(tile, xi, y);
          tile = get(xi, y);
          xi++;
        }
        if (xi > 3) {
          continue;
        }
        Game2048Tile newTile = get(xi, y);
        combine(tile, newTile);
      }
    }
  }

  private void left() {
    for (int x = 1; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        Game2048Tile tile = get(x, y);
        if (tile.isEmpty()) {
          continue;
        }
        int xi = x - 1;
        while (hasEmptyTile(xi, y)) {
          tile = get(xi + 1, y);
          move(tile, xi, y);
          tile = get(xi, y);
          xi--;

        }
        if (xi < 0) {
          continue;
        }
        Game2048Tile newTile = get(xi, y);
        combine(tile, newTile);
      }
    }
  }

  private void combine(Game2048Tile tile, Game2048Tile newTile) {
    if (tile.getValue() == newTile.getValue()) {
      newTile.incremenet();
      tile.setEmpty();

      didMove = true;
    }
  }

  private void move(Game2048Tile tile, int x, int y) {
    get(x, y).update(tile);
    get(tile.x, tile.y).setEmpty();
    didMove = true;
  }

  private void down() {
    for (int y = 1; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        Game2048Tile tile = get(x, y);
        if (tile.isEmpty()) {
          continue;
        }
        int yi = y - 1;
        while (hasEmptyTile(x, yi)) {
          tile = get(x, yi + 1);
          move(tile, x, yi);
          tile = get(x, yi);
          yi--;

        }
        if (yi < 0) {
          continue;
        }
        Game2048Tile newTile = get(x, yi);
        combine(tile, newTile);
      }
    }
  }

  public void place() {
    List<Game2048Tile> emptyTiles = getEmptyTiles();
    if (emptyTiles.isEmpty()) {
      Log.i(TAG, "Unable to place tile! No free slots!");
      return;
    }

    int randomFreeIndex = (int) (Math.random() * emptyTiles.size());
    Game2048Tile game2048Tile = emptyTiles.get(randomFreeIndex);
    game2048Tile.setValue(Math.random() > .9 ? v4 : v2);
  }

  private List<Game2048Tile> getEmptyTiles() {
    List<Game2048Tile> empty = new ArrayList<>();
    for (Game2048Tile tile : getItems()) {
      if (tile.isEmpty()) {
        empty.add(tile);
      }
    }
    return empty;
  }

  public Game2048Board copy() {
    Game2048Board copy = new Game2048Board();

    for (Game2048Tile tile : getItems()) {
      copy.set(tile);
    }
    return copy;
  }

  public void generateScore() {
    int score = 0;
    score = getSnakeScore();
//    score = getMonoticityScore();
//    List<Game2048Tile> sortedItems = getSortedItems();
//    Game2048Tile max = sortedItems.get(0);
//    if (tileInCorner(max)) {
//      score += 100 * max.getValue().VAL;
//      Game2048Tile second = sortedItems.get(1);
//      if (second.x == 3 && second.y == 2) {
//        score += 50 * sortedItems.get(1).getValue().VAL;
//
//        Game2048Tile third = sortedItems.get(2);
//        if (third.x == 3 && third.y == 1) {
//          score += 25 * third.getValue().VAL;
//        }
//      }
//    }
    // bonus for every empty tile
//    score += getEmptyTiles().size()  < 4 ? -1000 : 0;
//    score += getMaxScore() == get(3, 3).getValue().VAL ? 1000 : -1000;
//
//    int[][] logBoard = getLogBoard();
//    score -= logBoard[3][3] - (logBoard[3][2] + 1);
//    score -= logBoard[3][2] - (logBoard[3][1] + 1);
//    score -= logBoard[3][1] - (logBoard[3][0] + 1);

    setScore(score);
  }

  private int[][] getLogBoard() {
    int[][] logBoard = new int[4][4];
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        int val = get(x, y).getValue().VAL;
        logBoard[x][y] = val > 0 ? (int) (Math.log(val) / Math.log(2)) : 0;
      }
    }
    return logBoard;
  }

  private int getSnakeScore() {
    int score = 0;
    List<Game2048Tile> sortedItems = getSortedItems();

    // penalize if top right and the one below, are not the two highest
    if (get(3, 3).getValue() != sortedItems.get(0).getValue()) {
      score -= 20000;
    }
    if (get(3, 2).getValue() != sortedItems.get(1).getValue()) {
      score -= 10000;
    }
//
//    // penalize too many different cells
//    score += (Game2048Tile.VALUE.values().length - getUniqueValuesCount()) * 100;
//
//    // bonus if right most column is monotically decreasing from board max
    Game2048Tile max = sortedItems.get(0);
    if (max.getValue() == get(3, 3).getValue()) {
      score += 300000 * max.getValue().VAL;

      Game2048Tile second = sortedItems.get(1);
      if (second.x == 3 && second.y == 2) {
        score += sortedItems.get(1).getValue().VAL;

        Game2048Tile third = sortedItems.get(2);
        if (third.x == 3 && third.y == 1) {
          score += third.getValue().VAL;

          Game2048Tile forth = sortedItems.get(3);
          if (forth.x == 3 && forth.y == 0) {
            score += forth.getValue().VAL;

            Game2048Tile fifth = sortedItems.get(4);
            if (fifth.x == 2 && fifth.y == 0) {
              score += fifth.getValue().VAL;

              Game2048Tile sixth = sortedItems.get(5);
              if (sixth.x == 2 && sixth.y == 1) {
                score += 10 * sixth.getValue().VAL;

                Game2048Tile seventh = sortedItems.get(6);
                if (seventh.x == 2 && seventh.y == 2) {
                  score += 10 * seventh.getValue().VAL;
                }
              }
            }
          }
        }
      }
    }

    score += get(3, 3).getValue().VAL * 2048;
    score += get(3, 2).getValue().VAL * 1023;
    score += get(3, 1).getValue().VAL * 511;
    score += get(3, 0).getValue().VAL * 255;
    score += get(2, 0).getValue().VAL * 127;
    score += get(2, 1).getValue().VAL * 63;
    score += get(2, 2).getValue().VAL * 31;
    score += get(2, 3).getValue().VAL * 15;
//    score += get(3, 3).getValue().VAL > get(3, 2).getValue().VAL ? get(3, 3).getValue().VAL * 2048 : 0;
//    score += get(3, 2).getValue().VAL > get(3, 1).getValue().VAL ? get(3, 2).getValue().VAL * 1023 : 0;
//    score += get(3, 1).getValue().VAL > get(3, 0).getValue().VAL ? get(3, 1).getValue().VAL * 511 : 0;

    // penalize if there are low values in the two right most columns
    for (int y = 0; y < 3; y++) {
      if (get(3, y).getValue() == v0) {
        score -= 100 * sortedItems.get(0).getValue().VAL;
      } else if (get(3, y).getValue() == v2) {
        score -= 50 * sortedItems.get(0).getValue().VAL;
      } else if (get(3, y).getValue() == v4) {
        score -= 50 * sortedItems.get(0).getValue().VAL;
      }
    }

    // bonus for every empty tile
    score -= (getEmptyTiles().size() < 4) ? 20000 * getEmptyTiles().size() : 0;
    // bonus for the total score of the board
    for (Game2048Tile tile : getItems()) {
      score += tile.getValue().VAL;
    }

    return score;
  }

  private int getUniqueValuesCount() {
    Set<Integer> differentValues = new HashSet<>();
    for (Game2048Tile tile : getItems()) {
      differentValues.add(tile.getValue().VAL);
    }
    return differentValues.size();
  }

  public List<Game2048Tile> getSortedItems() {
    List<Game2048Tile> items = getItems();
    Collections.sort(items, new Comparator<Game2048Tile>() {
      @Override
      public int compare(Game2048Tile o1, Game2048Tile o2) {
        return o2.getValue().VAL - o1.getValue().VAL;
      }
    });

    return items;
  }


  public int getMaxScore() {
    Game2048Tile max = Collections.max(getItems(), new Comparator<Game2048Tile>() {
      @Override
      public int compare(Game2048Tile o1, Game2048Tile o2) {
        return o1.getValue().VAL - o2.getValue().VAL;
      }
    });

    return max.getValue().VAL;
  }

  private int getMonoticityScore() {
    int score = 0;
    for (int x = 0; x < 4; x++) {
      score += getRowScore(x);
    }

    for (int y = 0; y < 4; y++) {
      score += getColumnScore(y);
    }

    return score;
  }

  private int getRowScore(int row) {
    int score = 0;
    Game2048Tile y0 = get(0, row);
    Game2048Tile y1 = get(1, row);
    Game2048Tile y2 = get(2, row);
    Game2048Tile y3 = get(3, row);

    if (y0.getValue().VAL <= y1.getValue().VAL) {
      score += 4 * y0.getValue().VAL;
    }
    if (y1.getValue().VAL <= y2.getValue().VAL) {
      score += 2 * y1.getValue().VAL;
    }
    if (y2.getValue().VAL <= y3.getValue().VAL) {
      score += 1 * y2.getValue().VAL;
    }

    return score;
  }

  private int getColumnScore(int col) {
    int score = 0;
    Game2048Tile x0 = get(col, 0);
    Game2048Tile x1 = get(col, 1);
    Game2048Tile x2 = get(col, 2);
    Game2048Tile x3 = get(col, 3);

    if (x0.getValue().VAL <= x1.getValue().VAL) {
      score += 4 * x0.getValue().VAL;
    }
    if (x1.getValue().VAL <= x2.getValue().VAL) {
      score += 2 * x1.getValue().VAL;
    }
    if (x2.getValue().VAL <= x3.getValue().VAL) {
      score += 1 * x2.getValue().VAL;
    }

    return score;
  }

  @Override
  public MiniMaxState getParent() {
    return parent;
  }

  @Override
  public boolean hasParent() {
    return parent != null;
  }

  @Override
  public List<MiniMaxState> getPossibleNextStates() {
    generatePossibleNextStates();
    return possibleNextStates;
  }

  @Override
  public List<MiniMaxState> getOpposingStates() {
    generateOpposingStates();
    return opposingStates;
  }

  @Override
  public boolean isTerminal() {
    return getPossibleNextStates().size() == 0;
  }

  @Override
  public int getAlpha() {
    return a;
  }

  @Override
  public int getBeta() {
    return b;
  }

  @Override
  public void setAlpha(int a) {
    this.a = a;
  }

  @Override
  public void setBeta(int b) {
    this.b = b;
  }

  @Override
  public int compareTo(MiniMaxState o) {
    int score = getScore();
    int otherScore = o.getScore();
    return otherScore - score;
  }

  public boolean didMove() {
    return didMove;
  }

  public void generatePossibleNextStates() {
    List<MiniMaxState> boards = new ArrayList<>();
    for (AICanvas.Direction dir : AICanvas.Direction.values()) {
      Game2048Board next = copy();
      if (next.move(dir, false)) {
        boards.add(next);
      }
    }
    this.possibleNextStates = boards;
    children = new ArrayList<>();
    children.addAll(possibleNextStates);
  }

  public void generateOpposingStates() {
    List<MiniMaxState> opposingStates = new ArrayList<>();
    for (Game2048Tile tile : getEmptyTiles()) {
      Game2048Board opposing = copy();
      opposing.place(tile.x, tile.y);
      opposingStates.add(opposing);
    }
    this.opposingStates = opposingStates;
    children = new ArrayList<>();
    children.addAll(opposingStates);
  }

  @Override
  public void setParent(MiniMaxState state) {
    parent = state;
  }


  private void place(int x, int y) {
    Game2048Tile randomTile = get(x, y);
    randomTile.setValue(v2);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n" + lastMove + "\n");
    for (int y = 3; y >= 0; y--) {
      for (int x = 0; x < 4; x++) {
        sb.append(get(x, y).getValue() + "\t");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getScore() {
    if (score == null) {
      generateScore();
    }
    return score;
  }

  public void printBoard() {
    Log.v(TAG, toString());
  }

  public Game2048Board getChild(int bestVal) {
    for (MiniMaxState child : children) {
      if (child.getBeta() == bestVal) {
        return (Game2048Board) child;
      }
    }
    return null;
  }
}
