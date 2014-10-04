package puzzles.nono;

import java.util.List;

import ai.AIMain;
import ai.gui.AIGui;
import ai.models.AIAdapter;
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
    return null;
  }

  @Override
  protected void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<ColorTile> graph) {

  }

  @Override
  protected AIAdapter<ColorTile> generateAdapter(String input) {
    return null;
  }

  @Override
  protected AStarCspPuzzle getSamplePuzzle(int i) {
    return null;
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
