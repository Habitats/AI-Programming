package algorithms.csp.canonical_utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Patrick on 03.10.2014.
 */
public class VariableList implements Iterable<Variable<Integer>> {

  private final Map<String, Variable<Integer>> variableMap;

  public VariableList() {
    variableMap = new HashMap<>();
  }

  public Variable getVariable(String id) {
    return variableMap.get(id);
  }

  public Collection<Variable<Integer>> getAll() {
    return variableMap.values();
  }

  public Set<String> getIds() {
    return variableMap.keySet();
  }

  public void put(Variable var) {
    variableMap.put(var.getId(), var);
  }
  public void put(String id, Variable var){
    variableMap.put(id, var);
  }

  @Override
  public Iterator<Variable<Integer>> iterator() {
    return variableMap.values().iterator();
  }


  public boolean add(Variable variable) {
    int oldSize = size();
    put(variable);
    return oldSize != size();
  }


  public int size() {
    return variableMap.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public VariableList copy() {
    VariableList dupe = new VariableList();
    for (Variable var : variableMap.values()) {
      dupe.variableMap.put(var.getId(), var.copy());
    }
    return dupe;
  }
}
