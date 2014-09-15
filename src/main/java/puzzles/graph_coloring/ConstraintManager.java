package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.09.2014.
 */
public class ConstraintManager {

  private static ConstraintManager instance;
  private List<Constraint> constraints;
  private Map<String, List<Variable>> variableMap;

  public static ConstraintManager getManager() {
    if (instance == null) {
      instance = new ConstraintManager();
    }
    return instance;
  }

  private ConstraintManager() {
    variableMap = new HashMap<>();
  }

  private void generateConstraints(AIAdapter<ColorNode> graph, List<Variable> variables) {
    List<Constraint> constraints = new ArrayList<>();
    for (ColorNode node : graph.getItems()) {
      String id = node.getId();
      String expression = "";
      for (ColorNode child : node.getChildren()) {
        String cId = child.getId();
        expression += id + " != " + cId + " and ";
      }
      expression += "1 < 2";
      Constraint constraint = new Constraint(variables, expression);
      constraints.add(constraint);
    }
    this.constraints = constraints;
  }

  public List<Constraint> getConstraints() {
    return constraints;
  }


  public void initialize(AIAdapter graph, List<Variable> variables) {
    generateConstraints(graph,variables);
  }
}
