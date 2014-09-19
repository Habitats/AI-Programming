package puzzles.graph_coloring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

  //  generate constraints with the following format
  //  c1: a != b
  //  c2: a != c
  //  c3: a != d
  private void generateSimpleConstraints(AIAdapter<ColorNode> graph, List<Variable> variables) {
    Log.v(TAG, "Generating constraints ...");
    HashMap<String, Constraint> constraints = new HashMap<>();
    int count = 0;
    for (ColorNode node : graph.getItems()) {
      String id = node.getId();
      for (ColorNode child : node.getChildren()) {
        String cId = child.getId();
        int res = String.CASE_INSENSITIVE_ORDER.compare(id, cId);

        // remove duplicate constraints, but first we need to order the id's
        // duplication is handled automatically by using a hashmap on the Id
        String id1 = res < 0 ? id : cId;
        String id2 = id1 == id ? cId : id;
        String expression = id1 + " != " + id2;

        Constraint constraint = new Constraint(variables, expression);
        constraints.put(expression, constraint);
        Log.v(TAG, constraint);
        count++;
      }
    }

    this.constraints = new ArrayList<>(constraints.values());

    Log.v(TAG,
          "... finished generating " + constraints.size() + " constraints and removed " + (count - constraints.size())
          + " duplicates!");

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
    generateSimpleConstraints(graph, variables);


  }

  private class SortedVariableList extends ArrayList<String> {

    private final Comparator<String> comparator;

    public SortedVariableList() {
      comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
          int res = String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
          if (res == 0) {
            res = o1.compareTo(o2);
          }
          return res;
        }
      };
    }

    @Override
    public boolean add(String s) {
      super.add(s);
      Collections.sort(this, comparator);
      return true;
    }
  }
}
