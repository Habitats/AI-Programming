package algorithms.a_star_csp;

import ai.Log;
import ai.gui.AIGui;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import ai.models.graph.Graph;
import algorithms.a_star.AStar;
import algorithms.a_star.AStarCallback;
import algorithms.a_star.AStarNode;
import algorithms.csp.CspButtonListener;

/**
 * Created by Patrick on 01.10.2014.
 */
public abstract class AStarCsp implements CspButtonListener, Runnable {

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
  public void resetClicked() {
    astar.terminate();
  }

  @Override
  public void loadClicked() {

    String input = gui.getInput();
    puzzle = getPuzzleFromInput(input);
    astarCsp();

  }

  @Override
  public void run() {
    setGui(generateGui());
    getGui().setListener(this);
  }

  protected abstract AIGui generateGui();

  protected abstract AStarCspPuzzle generateCspPuzzle();

  protected abstract void initializeAdapter(AStarCspPuzzle puzzle, AIAdapter<ColorNode> graph);

  protected abstract Graph<ColorNode> generateAdapter(String input);

  abstract protected AStarCspPuzzle getSamplePuzzle(int i);


  protected AStarCspPuzzle getPuzzleFromInput(String input) {
    AStarCspPuzzle puzzle = generateCspPuzzle();
    puzzle.setGui(getGui());

    AIAdapter<ColorNode> graph = generateAdapter(input);
    setAdapter(graph);
    puzzle.setVariables(puzzle.generateVariables());
    initializeAdapter(puzzle, graph);

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

  public AIAdapter<ColorNode> getAdapter() {
    return adapter;
  }
}
