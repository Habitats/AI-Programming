package puzzles.game20480;

import java.util.List;

import static puzzles.game20480.Game2048Tile.VALUE.v0;
import static puzzles.game20480.Game2048Tile.VALUE.v2;
import static puzzles.game20480.Game2048Tile.VALUE.v4;

/**
 * Created by Patrick on 23.11.2014.
 */
public class Simple {

  private static final int WEIGHT_MONOTICITY_RIGHT_EDGE = 1;
  private static final int WEIGHT_EMPTY = -20000;
  private static final int WEIGHT_SNAKE = 1;
  private static final int WEIGHT_UNIQUE_COUNT = 1;
  private static final int WEIGHT_BOARD_SUM = 1;
  private static final int WEIGHT_LOW_VALUES_RIGHT = 1;
  private static final int WEIGHT_MAX_NOT_IN_CORNER = 1;
  private static final int WEIGHT_MAX_IN_CORNER = 100000;
  private static final double SCORE_SUM_POWER = 3;
  private static final int WEIGHT_MAX_AT_EDGE = 1;
  private static final String TAG = Simple.class.getSimpleName();
  private static final int WEIGHT_CLUSTER = -1000;

  public static void generateScore(Game2048Board board) {

    List<Game2048Tile> sortedItems = board.getSortedItems();

    // penalize if top right and the one below, are not the two highest
    int maxNotInCorner = 0;
    if (board.get(3, 3).getValue() != sortedItems.get(0).getValue()) {
      maxNotInCorner -= 2;
    }
    if (board.get(3, 2).getValue() != sortedItems.get(1).getValue()) {
      maxNotInCorner -= 1;
    }
//
//    // penalize too many different cells
    int uniqueCount = (Game2048Tile.VALUE.values().length - board.getUniqueValuesCount());
//
    int maxInCorner = 0;
    int monoticityOfRightEdge = 0;
//    // bonus if right most column is monotically decreasing from board max
    Game2048Tile max = sortedItems.get(0);
    if (max.getValue() == board.get(3, 3).getValue()) {
      monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);
      maxInCorner = 1;

      Game2048Tile second = sortedItems.get(1);
      if (second.x == 3 && second.y == 2) {
        monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);

        Game2048Tile third = sortedItems.get(2);
        if (third.x == 3 && third.y == 1) {
          monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);

          Game2048Tile forth = sortedItems.get(3);
          if (forth.x == 3 && forth.y == 0) {
            monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);

            Game2048Tile fifth = sortedItems.get(4);
            if (fifth.x == 2 && fifth.y == 0) {
              monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);

              Game2048Tile sixth = sortedItems.get(5);
              if (sixth.x == 2 && sixth.y == 1) {
                monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);

                Game2048Tile seventh = sortedItems.get(6);
                if (seventh.x == 2 && seventh.y == 2) {
                  monoticityOfRightEdge += Math.pow(max.getValue().VAL, SCORE_SUM_POWER);
                }
              }
            }
          }
        }
      }
    }

    int snake = 0;
    snake += Math.pow(board.get(3, 3).getValue().VAL, 8);
    snake += Math.pow(board.get(3, 2).getValue().VAL, 7);
    snake += Math.pow(board.get(3, 1).getValue().VAL, 6);
    snake += Math.pow(board.get(3, 0).getValue().VAL, 5);
    snake += Math.pow(board.get(2, 0).getValue().VAL, 4);
    snake += Math.pow(board.get(2, 1).getValue().VAL, 3);
    snake += Math.pow(board.get(2, 2).getValue().VAL, 2);
    snake += Math.pow(board.get(2, 3).getValue().VAL, 1);

    // penalize if there are low values in the two right most columns
    int lowValuesRight = 0;
    for (int y = 0; y < 3; y++) {
      if (board.get(3, y).getValue() == v0) {
        lowValuesRight -= 2 * sortedItems.get(0).getValue().VAL;
      } else if (board.get(3, y).getValue() == v2) {
        lowValuesRight -= sortedItems.get(0).getValue().VAL;
      } else if (board.get(3, y).getValue() == v4) {
        lowValuesRight -= sortedItems.get(0).getValue().VAL;
      }
    }

    // bonus for every empty tile
    int empty = 0;
    if (board.getEmptyTiles().size() < 4) {
      empty = board.getEmptyTiles().size();
    }
//    score -= 20000 * getEmptyTiles().size();

    // bonus for the total score of the board
    int boardSum = 0;
    for (Game2048Tile tile : board.getItems()) {
      boardSum += Math.pow(tile.getValue().VAL, SCORE_SUM_POWER);
    }

    int maxAtEdge = getMaxAtEdgeScore(max);

    int score =  //
        empty * WEIGHT_EMPTY +  //
//        snake * WEIGHT_SNAKE + //
//        monoticityOfRightEdge * WEIGHT_MONOTICITY_RIGHT_EDGE + //
//        maxNotInCorner * WEIGHT_MAX_NOT_IN_CORNER + //
//        uniqueCount * WEIGHT_UNIQUE_COUNT + //
//        lowValuesRight * WEIGHT_LOW_VALUES_RIGHT + //
//        maxInCorner * WEIGHT_MAX_IN_CORNER + //
        maxAtEdge * WEIGHT_MAX_AT_EDGE + //
        boardSum * WEIGHT_BOARD_SUM + //
//        clusteringScore(board) + WEIGHT_CLUSTER + //
        //
        0;

//    Log.v(TAG, "e: " + empty + " mae: " + maxAtEdge + " bs: " + boardSum);

    board.setScore(score);
  }

  private static int getMaxAtEdgeScore(Game2048Tile max) {
    int maxAtEdge = 0;
    if (max.x == 0 || max.x == 3 || max.y == 0 || max.y == 3) {
      maxAtEdge = 1;
    }

//    for (Game2048Tile tile : board.getItems()) {
//      if (tile.x == 3 || tile.x == 0 || tile.y == 0 || tile.y == 3) {
//        maxAtEdge += Math.pow(tile.getValue().VAL, 2);
//      }
//    }
    return maxAtEdge;
  }

  public static int clusteringScore(Game2048Board board) {
    int sum = 0;
    for (Game2048Tile tile : board.getItems()) {
      if (tile.getValue() == v0) {
        continue;
      }

      List<Game2048Tile> neighbors = board.getManhattanNeighbors(tile);
      for (Game2048Tile neighbor : neighbors) {
        sum += (Math.abs(tile.getValue().VAL - neighbor.getValue().VAL) / neighbors.size());
      }
    }
    return sum;
  }
}
