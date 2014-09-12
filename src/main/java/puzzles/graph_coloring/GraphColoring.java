package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.List;

import ai.models.AIAdapter;
import ai.models.ColorNode;
import algorithms.csp.Constraint;
import algorithms.csp.CspPuzzle;
import algorithms.csp.Domain;
import algorithms.csp.GAC;
import algorithms.csp.Variable;
import puzzles.graph_coloring.gui.GraphColoringGui;
import puzzles.graph_coloring.interfaces.GraphColoringButtonListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoring implements GraphColoringButtonListener, CspPuzzle {


  private GraphColoringGui gui;
  private List<Variable> variables;
  private List<Constraint> constraints;
  private int K = 4;

  public GraphColoring() {
    // initialize the GUI
    gui = new GraphColoringGui();
    gui.setListener(this);

    AIAdapter<ColorNode> graph = GraphInputUtils.generateGraph(GraphInputUtils.samples.get(0));
    generateVariables(graph);
    generateConstraints(graph);
    gui.setAdapter(graph);

    new GAC(this).run();
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
      generateVariables(graph);
      generateConstraints(graph);
      gui.setAdapter(graph);
    }
  }

  private void generateConstraints(AIAdapter<ColorNode> graph) {
    List<Constraint> constraints = new ArrayList<>();
    for (ColorNode node : graph.getItems()) {
      String id = node.getId();
      String expression = "";
      for (ColorNode child : node.getChildren()) {
        String cId = child.getId();
        expression += id + " != " + cId + " and ";
      }
      expression += "1 < 2";
      Constraint constraint = new Constraint(getVariables(), expression);
      constraints.add(constraint);
    }
    this.constraints = constraints;
  }

  private Domain getDomain() {
    int[] domain = new int[K];
    for (int i = 0; i < K; i++) {
      domain[i] = i;
    }
    return new Domain(domain);
  }

  private void generateVariables(AIAdapter<ColorNode> graph) {
    List<Variable> variables = new ArrayList<>();
    for (ColorNode node : graph.getItems()) {
      Variable var = new Variable(node.getId(), getDomain());
      variables.add(var);
    }
    variables.get(0).setAssumption(1);
    variables.get(1).setAssumption(2);
    this.variables = variables;
  }

  @Override
  public List<Constraint> getConstraints() {
    return constraints;
  }

  @Override
  public List<Variable> getVariables() {
    return variables;
  }

  @Override
  public void visualize() {

  }
}
