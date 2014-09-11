package puzzles.graph_coloring;

import ai.models.AIAdapter;
import ai.models.ColorNode;
import ai.models.Graph;
import algorithms.csp.GAC;
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

    AIAdapter<ColorNode> graph = GraphInputUtils.generateGraph(GraphInputUtils.samples.get(0));
    gui.setAdapter(graph);

    new GAC().run();
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

  @Override
  public void sampleSelected(int i) {
    if (GraphInputUtils.samples.size() > i) {
      Graph<ColorNode> graph = GraphInputUtils.generateGraph(GraphInputUtils.samples.get(i));
      gui.setAdapter(graph);
    }
  }
}
