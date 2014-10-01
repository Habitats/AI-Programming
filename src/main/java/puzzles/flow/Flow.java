package puzzles.flow;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.grid.Tile;
import algorithms.a_star_csp.AStarCsp;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspButtonListener;
import puzzles.flow.gui.FlowGui;

/**
 * Created by Patrick on 01.10.2014.
 */
public class Flow extends AStarCsp<Tile> implements CspButtonListener, Runnable {

  public static final String TAG = AIMain.class.getSimpleName();

  @Override
  protected AIGui generateGui() {
    return new FlowGui();
  }

  @Override
  protected AStarCspPuzzle generateCspPuzzle() {
    return new FlowCspPuzzle(this);
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
    if (i >= FlowUtils.samples.size()) {
      return null;
    }
    return getPuzzleFromInput(FlowUtils.samples.get(i));
  }
}
