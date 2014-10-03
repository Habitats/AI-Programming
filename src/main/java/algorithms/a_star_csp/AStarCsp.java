package algorithms.a_star_csp;

import ai.Log;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.Node;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.csp.CspButtonListener;

/**
 * Created by Patrick on 01.10.2014.
 */
public abstract class AStarCsp<T extends Node> implements CspButtonListener, Runnable {

  private static final String TAG = AStarCsp.class.getSimpleName();
  private AStar astar;
  private AIAdapter adapter;
  private AIGui gui;
  private AStarCspPuzzle puzzle;


  public void setAdapter(AIAdapter graph) {
    this.adapter = graph;
    gui.setAdapter(graph);
  }

  public AIGui getGui() {
    return gui;
  }

  public void setGui(AIGui gui) {
    this.gui = gui;
  }

  private void astarCsp() {

    AStarNode start = new AStarCspNode(puzzle);
    astar = new AStar(start, new AStarCallback() {

      @Override
      public void finished(AStarNode best, AStar aStar) {
        Log.i(AStarCsp.TAG, "A* success! ");
        Log.i(AStarCsp.TAG, best);
        Log.i(AStarCsp.TAG, "Number of assumptions made: " + best.getPathLength());
      }

      @Override
      public void error() {
        Log.i(AStarCsp.TAG, "A* failed to find a solution!");
      }
    });
    astar.runInBackground();
  }

  @Override
  public void runClicked() {
    astarCsp();
  }

  @Override
  public void resetClicked() {
    astar.terminate();
  }

  @Override
  public void loadClicked() {

    String input = gui.getInput();
    puzzle = getPuzzleFromInput(input);
  }

  @Override
  public void run() {
    setGui(generateGui());
    getGui().setListener(this);
  }

  protected abstract AIGui generateGui();

  protected abstract AStarCspPuzzle generateCspPuzzle();

  protected abstract void generateConstraints(AStarCspPuzzle puzzle, AIAdapter<T> graph);

  protected abstract AIAdapter<T> generateAdapter(String input);

  abstract protected AStarCspPuzzle getSamplePuzzle(int i);


  protected AStarCspPuzzle getPuzzleFromInput(String input) {
    AStarCspPuzzle puzzle = generateCspPuzzle();

    AIAdapter<T> adapter = generateAdapter(input);
    setAdapter(adapter);
    puzzle.setVariables(puzzle.generateVariables());
    generateConstraints(puzzle, adapter);

    return puzzle;
  }


  @Override
  public void stepClicked() {
    synchronized (astar) {
      astar.notify();
    }
  }

  @Override
  public void stepChanged(int value) {
  }

  @Override
  public void sampleSelected(int i) {
//    this.puzzle = getSamplePuzzle(i);
  }

  public AIAdapter<T> getAdapter() {
    return adapter;
  }
}
