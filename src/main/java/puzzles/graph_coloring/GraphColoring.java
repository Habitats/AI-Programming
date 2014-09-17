package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.List;

import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star_csp.AStarCspPuzzle;
import algorithms.csp.CspPuzzle;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;
import puzzles.graph_coloring.gui.GraphColoringGui;
import puzzles.graph_coloring.gui.interfaces.GraphColoringButtonListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public class GraphColoring implements GraphColoringButtonListener, CspPuzzle, AStarCspPuzzle {


  private GraphColoringGui gui;
  private int K = 4;
  private List<Variable> variables;
  private AIAdapter<ColorNode> adapter;


  public void setGui(GraphColoringGui gui) {
    this.gui = gui;
    gui.setListener(this);
  }

  public List<Variable> generateVariables() {
    List<Variable> variables = new ArrayList<>();
    for (ColorNode node : adapter.getItems()) {
      Variable var = new Variable(node.getId(), getInitialDomain());
      variables.add(var);
      var.setListener(node);
    }
    return variables;
  }

  private Domain getInitialDomain() {
    int[] domain = new int[K];
    for (int i = 0; i < K; i++) {
      domain[i] = i;
    }
    return new Domain(domain);
  }

  // GraphColoringButtonListener ///////////////////////

  @Override
  public void setAdapter(AIAdapter graph) {
    this.adapter = graph;
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


  // CspPuzzle /////////////////////////////////////////////////////////////
  @Override
  public List<Constraint> getConstraints() {
    return ConstraintManager.getManager().getConstraints();
  }

  @Override
  public List<Variable> getVariables() {
    return variables;
  }

  @Override
  public int getDomainSize() {
    int size = 0;
    for (Variable variable : getVariables()) {
      size += variable.getDomain().getSize();
    }
    return size;
  }

  @Override
  public void visualize() {
  }

  // AStarCspPuzzle /////////////////////////////////////////////////////////////////

  @Override
  public AStarCspPuzzle duplicate() {
    GraphColoring dupe = new GraphColoring();
    dupe.setGui(gui);
    dupe.setAdapter(adapter);
    dupe.setVariables(dupe.generateVariables());
    for(int i = 0; i < getVariables().size();i++){
      dupe.getVariables().get(i).copyDomain(this.getVariables().get(i));
      dupe.getVariables().get(i).setValue(this.getVariables().get(i).getValue());
    }
    return dupe;
  }

  @Override
  public Variable getVariable(String id) {
    for (Variable var : getVariables()) {
      if (var.getId().equals(id)) {
        return var;
      }
    }
    return null;
  }

  @Override
  public String getId() {
    StringBuilder sb = new StringBuilder();
    for (Variable var : getVariables()) {
      sb.append(var.getId() + var.getDomain().toString());
    }
    return sb.toString();
  }

  @Override
  public Variable getSuccessor() {
    int min = Integer.MAX_VALUE;
    Variable minVar = null;
    for (Variable var : getVariables()) {
      if (var.getDomain().getSize() < min) {
        min = var.getDomain().getSize();
        minVar = var;
      }
    }
    return minVar;
  }

  public void setVariables(List<Variable> variables) {
    this.variables = variables;
  }
}
