package puzzles.graph_coloring;

import puzzles.graph_coloring.gui.GraphColoringGui;
import puzzles.graph_coloring.interfaces.GraphColoringButtonListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoring implements GraphColoringButtonListener {


  private final GraphColoringGui gui;

  public GraphColoring() {
    // initialize the GUI
    gui = new GraphColoringGui();
    gui.setListener(this);
  }

  @Override
  public void resetClicked() {

  }

  @Override
  public void loadClicked() {

  }

  @Override
  public void stepClicked() {

  }

  @Override
  public void stepChanged(int value) {

  }
}
