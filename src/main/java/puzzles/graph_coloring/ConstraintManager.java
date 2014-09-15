package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.List;

import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.09.2014.
 */
public class ConstraintManager {

  private static ConstraintManager instance;
  private List<Variable> variables;
  private List<Constraint> constraints;
  private int K = 4;

  public static ConstraintManager getManager() {
    if (instance == null) {
      instance = new ConstraintManager();
    }
    return instance;
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

  private void generateVariables(AIAdapter<ColorNode> graph) {
    List<Variable> variables = new ArrayList<>();
    for (ColorNode node : graph.getItems()) {
      Variable var = new Variable(node.getId(), getInitialDomain());
      variables.add(var);
      var.setListener(node);
    }
    this.variables = variables;
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public List<Constraint> getConstraints() {
    return constraints;
  }

  private Domain getInitialDomain() {
    int[] domain = new int[K];
    for (int i = 0; i < K; i++) {
      domain[i] = i;
    }
    return new Domain(domain);
  }

  public void initialize(AIAdapter graph) {
    generateVariables(graph);
    generateConstraints(graph);
  }
}
