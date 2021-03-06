package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ai.Log;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.csp.canonical_utils.CanonicalConstraint;
import algorithms.csp.canonical_utils.Constraint;
import algorithms.csp.canonical_utils.Variable;
import algorithms.csp.canonical_utils.VariableList;

/**
 * Created by Patrick on 15.09.2014.
 */
public class GraphColoringConstraintManager {

  private static final String TAG = GraphColoringConstraintManager.class.getSimpleName();
  private static GraphColoringConstraintManager instance;

  private final HashMap<String, Integer> variableCountMap;
  private final List<Constraint> constraints;

  public static GraphColoringConstraintManager getManager() {
    if (instance == null) {
      instance = new GraphColoringConstraintManager();
    }
    return instance;
  }

  private GraphColoringConstraintManager() {
    variableCountMap = new HashMap<>();
    constraints = new ArrayList<>();
  }

  //  generate constraints with the following format
  //  c1: a != b
  //  c2: a != c
  //  c3: a != d
  public void generateConstraints(AIAdapter<ColorNode> graph, VariableList variables) {
    Log.v(TAG, "Generating constraints ...");
    HashMap<String, Constraint> constraints = new HashMap<>();
    int count = 0;
    for (ColorNode node : graph.getItems()) {
      String id = node.getId();
      for (ColorNode child : node.getChildren()) {
        String cId = child.getId();
        int res = String.CASE_INSENSITIVE_ORDER.compare(id, cId);

        // remove copy constraints, but first we need to order the id's
        // duplication is handled automatically by using a hashmap on the Id
        String id1 = res < 0 ? id : cId;
        String id2 = id1 == id ? cId : id;
        String expression = id1 + " != " + id2;

        Constraint constraint = new CanonicalConstraint(variables, expression);
        constraints.put(expression, constraint);
        Log.i(TAG, constraint);
        count++;
      }
    }

    List<Constraint> immutableConstraints = Collections.unmodifiableList(new ArrayList<>(constraints.values()));
    setConstraints(immutableConstraints);

    Log.i(TAG, "... finished generating " + constraints.size() + " constraints and filtered out " + (count - constraints
        .size()) + " duplicates!");

    generateVariableCounts();

    for (Variable variable : variables) {
      variable.setConstraintsContainingVariable(immutableConstraints);
    }
  }

  private void generateVariableCounts() {
    for (Constraint<Variable> constraint : this.constraints) {
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


  public void setConstraints(List<Constraint> constraints) {
    this.constraints.clear();
    this.constraints.addAll(constraints);
  }

}
