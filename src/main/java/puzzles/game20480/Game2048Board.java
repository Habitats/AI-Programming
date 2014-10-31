package puzzles.game20480;

import java.util.ArrayList;
import java.util.List;

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
    for (Game2048Tile tile : getItems()) {
      score += tile.getValue().VAL;
    }
    setScore(score);
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
    return false;
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
  }

  public void generateOpposingStates() {
    List<MiniMaxState> opposingStates = new ArrayList<>();
    for (Game2048Tile tile : getEmptyTiles()) {
      Game2048Board opposing = copy();
      opposing.place(tile.x, tile.y);
      opposingStates.add(opposing);
    }
    this.opposingStates = opposingStates;
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
    Log.v(TAG, "\n" + lastMove + "\n" + toString());
  }
}
