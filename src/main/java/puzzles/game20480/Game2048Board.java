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
  private Double probability;

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

  public List<Game2048Tile> getEmptyTiles() {
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

  public int getUniqueValuesCount() {
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
  public List<MiniMaxState> getAllOpposingStates() {
    generateAllOpposingStates();
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
  public double getProbability() {
    return probability;
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

  public void generateAllOpposingStates() {
    List<MiniMaxState> opposingStates = new ArrayList<>();
    Set<Double> probs = new HashSet<>();
    for (Game2048Tile tile : getEmptyTiles()) {
      Game2048Board opposing = copy();
      double prob = Math.random() < .9 ? .1 : .9;
      opposing.setProbability(prob);
      opposing.place(tile.x, tile.y, prob);
      opposingStates.add(opposing);
    }
    this.opposingStates = opposingStates;
    children = new ArrayList<>();
    children.addAll(opposingStates);
  }

  private void place(int x, int y, Double prob) {
    Game2048Tile randomTile = get(x, y);
    randomTile.setValue(prob == 0.9 ? v2 : v4);
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
//      Snake.generateScore(this);
//      Simple.generateScore(this);
      FourOutOfTen.bitch(this);
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

  public void setProbability(Double probability) {
    this.probability = probability;
  }
}
