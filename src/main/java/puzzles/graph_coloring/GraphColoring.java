package puzzles.graph_coloring;

import java.util.List;

import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import puzzles.graph_coloring.gui.GraphColoringGui;
import puzzles.graph_coloring.gui.interfaces.GraphColoringButtonListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoring implements GraphColoringButtonListener, CspPuzzle {


  private GraphColoringGui gui;
  private List<Variable> variables;
  private List<Constraint> constraints;
  private int K = 4;
  private AIAdapter adapter;

  @Override
  public void setAdapter(AIAdapter graph) {
    this.adapter = graph;
    ConstraintManager.getManager().initialize(graph);
    gui.setAdapter(graph);
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
      AIAdapter<ColorNode> graph = GraphInputUtils.generateGraph(GraphInputUtils.samples.get(i));
      gui.setAdapter(graph);
    }
  }


  @Override
  public List<Constraint> getConstraints() {
    return ConstraintManager.getManager().getConstraints();
  }

  @Override
  public List<Variable> getVariables() {
    return ConstraintManager.getManager().getVariables();
  }

  @Override
  public void visualize() {
  }

  @Override
  public String getId() {
    StringBuilder sb = new StringBuilder();
    for (Variable var : getVariables()) {
      sb.append(var.getId() + var.getDomain().toString());
    }
    return sb.toString();
  }

  public void setGui(GraphColoringGui gui) {
    this.gui = gui;
    gui.setListener(this);
  }
}
