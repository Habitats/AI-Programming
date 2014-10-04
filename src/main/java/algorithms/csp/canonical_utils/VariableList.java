package algorithms.csp.canonical_utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Patrick on 03.10.2014.
 */
public class VariableList implements Iterable<Variable> {

  private Map<String, Variable> variableMap;

  public VariableList() {
    variableMap = new HashMap<>();
  }

  public Variable getVariable(String id) {
    return variableMap.get(id);
  }

  public java.util.Collection<Variable> getAll() {
    return variableMap.values();
  }

  public java.util.Set<String> getIds() {
    return variableMap.keySet();
  }

  public void put(Variable var) {
    variableMap.put(var.getId(), var);
  }

  @Override
  public Iterator<Variable> iterator() {
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

}
