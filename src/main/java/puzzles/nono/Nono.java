package puzzles.nono;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Tile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import puzzles.nono.gui.NonoGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Nono extends AStarCsp<Tile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();

  @Override
  protected AIGui generateGui() {
    return new NonoGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new NonoCspPuzzle(this);
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<Tile> adapter) {
    //TODO: gen constraints
  }

  @Override
  protected AIAdapter<Tile> generateAdapter(String input) {
    //TODO: implement this
    return null;
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    if (i >= NonoUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(NonoUtils.samples.get(i));
  }
}
