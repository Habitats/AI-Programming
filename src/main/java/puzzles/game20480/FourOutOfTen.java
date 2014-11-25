package puzzles.game20480;

import java.util.List;

import ai.Log;

import static puzzles.game20480.Game2048Tile.VALUE.v0;
import static puzzles.game20480.Game2048Tile.VALUE.v2;

/**
 * Created by Patrick on 23.11.2014.
 */
public class FourOutOfTen {

  private static final String TAG = FourOutOfTen.class.getSimpleName();

  public static void bitch(Game2048Board b, boolean log) {
    List<Game2048Tile> sortedItems = b.getSortedItems();
    Game2048Tile max = sortedItems.get(0);

    int maxNotRight = 0;
    int snake = 0;
    int lowRights = 0;
    int empty = 0;
    int sum = 0;

    maxNotRight = maxNotRight(b, sortedItems);
    snake = snake(b, sortedItems, max);
    lowRights = lowRights(b, sortedItems);
    empty = empty(b);
    sum = sum(b);

    int score = maxNotRight + snake + lowRights + empty + sum;

    if (log) {
      Log.v(TAG,
            "mnr: " + maxNotRight + " \tsnake: " + snake + " \tlr: " + lowRights + " \te: " + empty + " \ts:" + sum);
    }

    b.setScore(score);
  }

  private static int sum(Game2048Board b) {
    // exponential bonus for sum of the board
    int score = 0;
    for (Game2048Tile tile : b.getItems()) {
      score += Math.pow(tile.getValue().VAL, 2);
    }
    return score;
  }

  private static int maxNotRight(Game2048Board b, List<Game2048Tile> sortedItems) {
    // penalty if max two values are not at the upper right
    int score = 0;
    if (b.get(3, 3).getValue() != sortedItems.get(0).getValue()) {
      score -= 2000000;
    } else {
      if (b.get(3, 2).getValue() != sortedItems.get(1).getValue()) {
        score -= 1000000;
      }
    }

    return score;
  }

  private static int empty(Game2048Board b) {
    // bonus for empty tiles
    int score = b.getEmptyTiles().size() * 1000;
    return score;
  }

  private static int lowRights(Game2048Board b, List<Game2048Tile> sortedItems) {
    // penalize if low values to the right
    int score = 0;
    for (int y = 0; y < 3; y++) {
      if (b.get(3, y).getValue() == v0) {
        score -= 100 * sortedItems.get(0).getValue().VAL;
      } else if (b.get(3, y).getValue() == v2) {
        score -= 50 * sortedItems.get(0).getValue().VAL;
      }
    }

    if (b.get(3, 3).getValue() == v0) {
      score -= 1000000;
    }
    if (b.get(3, 2).getValue() == v0) {
      score -= 500000;
    }
    if (b.get(3, 1).getValue() == v0) {
      score -= 250000;
    }

    if (b.get(3, 3).getValue() == v2) {
      score -= 250000;
    }
    if (b.get(3, 2).getValue() == v2) {
      score -= 100000;
    }
    if (b.get(3, 1).getValue() == v2) {
      score -= 50000;
    }

    return score;
  }

  private static int snake(Game2048Board b, List<Game2048Tile> sortedItems, Game2048Tile max) {
    // bonus for a snake-like patterns from the upper right, and down
    int score = 0;
    if (b.get(3, 3).getValue() == max.getValue()) {
      score += 30000 * max.getValue().VAL;

      Game2048Tile second = sortedItems.get(1);
      if (b.get(3, 2).getValue() == second.getValue()) {
        score += 100 * sortedItems.get(1).getValue().VAL;

        Game2048Tile third = sortedItems.get(2);
        if (b.get(3, 1).getValue() == third.getValue()) {
          score += third.getValue().VAL;

          Game2048Tile forth = sortedItems.get(3);
          if (b.get(3, 0).getValue() == forth.getValue()) {
            score += forth.getValue().VAL;

            Game2048Tile fifth = sortedItems.get(4);
            if (b.get(2, 0).getValue() == fifth.getValue()) {
              score += fifth.getValue().VAL;

              Game2048Tile sixth = sortedItems.get(5);
              if (b.get(2, 1).getValue() == sixth.getValue()) {
                score += 10 * sixth.getValue().VAL;

                Game2048Tile seventh = sortedItems.get(6);
                if (b.get(2, 2).getValue() == seventh.getValue()) {
                  score += 10 * seventh.getValue().VAL;
                }
              }
            }
          }
        }
      } else if (second.x == 0) {
        score -= 100;
      } else if (second.x == 1) {
        score -= 10;
      } else if (second.x == 2) {
        score -= 5;
      }
    } else if (max.x == 0) {
      score -= 300;
    } else if (max.x == 1) {
      score -= 20;
    } else if (max.x == 2) {
      score -= 10;
    }
    return score;
  }


  private static boolean tileInCorner(Game2048Tile max) {
    return (max.x == 3 && max.y == 3);
  }
}
