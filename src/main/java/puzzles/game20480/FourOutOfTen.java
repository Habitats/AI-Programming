package puzzles.game20480;

import java.util.List;

import static puzzles.game20480.Game2048Tile.VALUE.v0;
import static puzzles.game20480.Game2048Tile.VALUE.v2;

/**
 * Created by Patrick on 23.11.2014.
 */
public class FourOutOfTen {

  public static void bitch(Game2048Board b) {
    int score = 0;
    List<Game2048Tile> sortedItems = b.getSortedItems();
    Game2048Tile max = sortedItems.get(0);
    if (b.get(3, 3).getValue() != sortedItems.get(0).getValue()) {
      score -= 20000;
    }
    if (b.get(3, 2).getValue() != sortedItems.get(1).getValue()) {
      score -= 10000;
    }
//    score += (Game2048Tile.VALUE.values().length - b.getUniqueValuesCount()) * 100;
    if (tileInCorner(max)) {
      score += 30000 * max.getValue().VAL;
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
    for (int y = 0; y < 3; y++) {
      if (b.get(3, y).getValue() == v0) {
        score -= 100 * sortedItems.get(0).getValue().VAL;
      } else if (b.get(3, y).getValue() == v2) {
        score -= 50 * sortedItems.get(0).getValue().VAL;
      }
    }
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        Game2048Tile game2048Tile = b.get(x, y);
        if (game2048Tile.isEmpty()) {
          score += 200;
        }
      }
    }
    score += b.getEmptyTiles().size() * 1000;
    for (Game2048Tile tile : b.getItems()) {
      score += Math.pow(tile.getValue().VAL, 1.5);
    }
    b.setScore(score);
  }


  private static boolean tileInCorner(Game2048Tile max) {
    return (max.x == 3 && max.y == 3);
  }
}
