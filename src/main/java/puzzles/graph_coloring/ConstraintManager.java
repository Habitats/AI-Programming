package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.Log;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.09.2014.
 */
public class ConstraintManager {

  private static final String TAG = ConstraintManager.class.getSimpleName();
  private static ConstraintManager instance;
  private final HashMap<String, Integer> variableCountMap;
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
    variableCountMap = new HashMap<>();
  }

  private void generateConstraints(AIAdapter<ColorNode> graph, List<Variable> variables) {
    Log.v(TAG, "Generating constraints ...");
    List<Constraint> constraints = new ArrayList<>();
    for (ColorNode node : graph.getItems()) {
      String id = node.getId();
      String expression = "";
      for (ColorNode child : node.getChildren()) {
        String cId = child.getId();
        expression += " and " + id + " != " + cId;
      }
      expression = expression.substring(5);
      Constraint constraint = new Constraint(variables, expression);
      constraints.add(constraint);
      Log.v(TAG, constraint);
    }
    this.constraints = constraints;

    Log.v(TAG, "... finished generating " + constraints.size() + " constraints!");

    generateVariableCounts();
  }

  private void generateVariableCounts() {
    for (Constraint constraint : this.constraints) {
      for (Variable variable : constraint.getVariables()) {
        if (variableCountMap.containsKey(variable.getId())) {
          variableCountMap.put(variable.getId(), variableCountMap.get(variable.getId()) + 1);
        } else {
          variableCountMap.put(variable.getId(), 1);
        }
      }
    }
  }

  public int getConstrainedCount(Variable var) {
    return variableCountMap.get(var.getId());
  }


  public List<Constraint> getConstraints() {
    return constraints;
  }


  public void initialize(AIAdapter graph, List<Variable> variables) {
    generateConstraints(graph, variables);
  }
}
