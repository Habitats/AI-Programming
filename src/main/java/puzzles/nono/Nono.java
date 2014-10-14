package puzzles.nono;

import java.util.Arrays;
import java.util.List;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Board;
import ai.models.grid.ColorTile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import algorithms.csp.canonical_utils.Constraint;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Nono extends AStarCsp<ColorTile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();


  @Override
  protected AIGui generateGui() {
    return null;
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new NonoCspPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<ColorTile> graph) {

  }

  @Override
  protected AIAdapter<ColorTile> generateAdapter(String input) {
    List<String> inputList = Arrays.asList(input.split("\\n"));
    int width = Integer.parseInt(inputList.get(0).split("\\s+")[0]);
    int height = Integer.parseInt(inputList.get(0).split("\\s+")[1]);
    int numberOfColors = 2;

    Board<ColorTile> board = new Board(width, height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        ColorTile tile = new ColorTile(x, y, numberOfColors);
        board.set(tile);
      }
    }

    return null;
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= NonoUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(NonoUtils.samples.get(i));
  }

  @Override
  public int getDomainSize() {
    return 0;
  }

  @Override
  public List<Constraint> getConstraints() {
    return null;
  }
}
