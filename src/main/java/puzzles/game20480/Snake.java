package puzzles.game20480;

import java.util.List;

import static puzzles.game20480.Game2048Tile.VALUE.v0;
import static puzzles.game20480.Game2048Tile.VALUE.v2;
import static puzzles.game20480.Game2048Tile.VALUE.v4;

/**
 * Created by Patrick on 23.11.2014.
 */
public class Snake {

  public static void generateScore(Game2048Board board) {
    int score = 0;
    score = getSnakeScore(board);
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

    board.setScore(score);
  }

  private static int getSnakeScore(Game2048Board board) {
    int score = 0;
    List<Game2048Tile> sortedItems = board.getSortedItems();

    // penalize if top right and the one below, are not the two highest
    if (board.get(3, 3).getValue() != sortedItems.get(0).getValue()) {
      score -= 20000;
    }
    if (board.get(3, 2).getValue() != sortedItems.get(1).getValue()) {
      score -= 10000;
    }
//
//    // penalize too many different cells
    score += (Game2048Tile.VALUE.values().length - board.getUniqueValuesCount()) * 100;
//
//    // bonus if right most column is monotically decreasing from board max
    Game2048Tile max = sortedItems.get(0);
    if (max.getValue() == board.get(3, 3).getValue()) {
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

    score += board.get(3, 3).getValue().VAL * 2048;
    score += board.get(3, 2).getValue().VAL * 1023;
    score += board.get(3, 1).getValue().VAL * 511;
    score += board.get(3, 0).getValue().VAL * 255;
    score += board.get(2, 0).getValue().VAL * 127;
    score += board.get(2, 1).getValue().VAL * 63;
    score += board.get(2, 2).getValue().VAL * 31;
    score += board.get(2, 3).getValue().VAL * 15;
//    score += get(3, 3).getValue().VAL > get(3, 2).getValue().VAL ? get(3, 3).getValue().VAL * 2048 : 0;
//    score += get(3, 2).getValue().VAL > get(3, 1).getValue().VAL ? get(3, 2).getValue().VAL * 1023 : 0;
//    score += get(3, 1).getValue().VAL > get(3, 0).getValue().VAL ? get(3, 1).getValue().VAL * 511 : 0;

    // penalize if there are low values in the two right most columns
    for (int y = 0; y < 3; y++) {
      if (board.get(3, y).getValue() == v0) {
        score -= 100 * sortedItems.get(0).getValue().VAL;
      } else if (board.get(3, y).getValue() == v2) {
        score -= 50 * sortedItems.get(0).getValue().VAL;
      } else if (board.get(3, y).getValue() == v4) {
        score -= 50 * sortedItems.get(0).getValue().VAL;
      }
    }

    // bonus for every empty tile
    score -= (board.getEmptyTiles().size() < 4) ? 20000 * board.getEmptyTiles().size() : 0;
//    score -= 20000 * getEmptyTiles().size();
    // bonus for the total score of the board
    for (Game2048Tile tile : board.getItems()) {
      score += tile.getValue().VAL;
    }

    return score;
  }
}
