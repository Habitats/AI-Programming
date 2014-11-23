package puzzles.game20480;

import java.util.List;

import static puzzles.game20480.Game2048Tile.VALUE.v0;

/**
 * Created by Patrick on 23.11.2014.
 */
public class Simple {

  private static final int WEIGHT_MONOTICITY_RIGHT_EDGE = 1;
  private static final int WEIGHT_EMPTY = -10000;
  private static final int WEIGHT_SNAKE = 1;
  private static final int WEIGHT_UNIQUE_COUNT = 1;
  private static final int WEIGHT_BOARD_SUM = 1;
  private static final int WEIGHT_LOW_VALUES_RIGHT = 1;
  private static final int WEIGHT_MAX_NOT_IN_CORNER = 100000;
  private static final int WEIGHT_MAX_IN_CORNER = 100000;
  private static final double SCORE_SUM_POWER = 2;
  private static final int WEIGHT_MAX_AT_EDGE = 100000;
  private static final String TAG = Simple.class.getSimpleName();
  private static final int WEIGHT_CLUSTER = -1000;

  public static void generateScore(Game2048Board board) {

    List<Game2048Tile> sortedItems = board.getSortedItems();
    Game2048Tile max = sortedItems.get(0);

    if (board.getEmptyTiles().size() == 0) {
      board.setScore(0);
      return;
    }

    double score = //
        getEmtpyScore(board) * WEIGHT_EMPTY +  //
//        getSnake(board) * WEIGHT_SNAKE + //
//        getMonoticityOfRightEdge(board,sortedItems) * WEIGHT_MONOTICITY_RIGHT_EDGE + //
//        getMaxNotInCorner(board, sortedItems) * WEIGHT_MAX_NOT_IN_CORNER + //
//        getMaxAtEdgeScore(max) * WEIGHT_MAX_AT_EDGE + //
        getBoardSum(board) * WEIGHT_BOARD_SUM + //
//        clusteringScore(board) + WEIGHT_CLUSTER + //
        //
        0;

//    Log.v(TAG, "e: " + empty + " mae: " + maxAtEdge + " bs: " + boardSum);

    board.setScore((int) score);
  }

  private static int getMaxNotInCorner(Game2048Board board, List<Game2048Tile> sortedItems) {
    // penalize if top right and the one below, are not the two highest
    int maxNotInCorner = 0;
    if (board.get(3, 3).getValue() != sortedItems.get(0).getValue()) {
      maxNotInCorner -= 2;
    }
    if (board.get(3, 2).getValue() != sortedItems.get(1).getValue()) {
      maxNotInCorner -= 1;
    }
    return maxNotInCorner;
  }

  private static double getMonoticityOfRightEdge(Game2048Board board, List<Game2048Tile> sortedItems) {
    Game2048Tile max = sortedItems.get(0);

    double monoticityOfRightEdge = 0;
    if (max.getValue() == board.get(3, 3).getValue()) {
      monoticityOfRightEdge = Math.pow(max.getValue().VAL, SCORE_SUM_POWER);

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

    return monoticityOfRightEdge;

  }

  private static int getSnake(Game2048Board board) {
    int snake = 0;
    snake += Math.pow(board.get(3, 3).getValue().VAL, 8);
    snake += Math.pow(board.get(3, 2).getValue().VAL, 7);
    snake += Math.pow(board.get(3, 1).getValue().VAL, 6);
    snake += Math.pow(board.get(3, 0).getValue().VAL, 5);
    snake += Math.pow(board.get(2, 0).getValue().VAL, 4);
    snake += Math.pow(board.get(2, 1).getValue().VAL, 3);
    snake += Math.pow(board.get(2, 2).getValue().VAL, 2);
    snake += Math.pow(board.get(2, 3).getValue().VAL, 1);

    return snake;
  }

  private static double getEmtpyScore(Game2048Board board) {
    // bonus for every empty tile
    double empty = 0;
    if (board.getEmptyTiles().size() < 4) {
      empty = (4 - board.getEmptyTiles().size());
    }
    return empty;
  }

  private static int getBoardSum(Game2048Board board) {
    // bonus for the total score of the board
    int boardSum = 0;
    for (Game2048Tile tile : board.getItems()) {
      boardSum += Math.pow(tile.getValue().VAL, SCORE_SUM_POWER);
    }
    return boardSum;
  }

  private static int getMaxAtEdgeScore(Game2048Tile max) {
    int maxAtEdge = 0;
    if (max.x == 0 || max.x == 3) {
      maxAtEdge += 1;
    }
    if (max.y == 0 || max.y == 3) {
      maxAtEdge += 1;
    }

    if (maxAtEdge == 2) {
      maxAtEdge += 20;
    }

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
