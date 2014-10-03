package algorithms.csp.canonical_utils;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Patrick on 03.10.2014.
 */
public class VariableList implements Collection<Variable> {

  private Map<String, Variable> variableMap;

  public VariableList() {
    variableMap = new HashMap<>();
  }

  public void getVariable(String id) {
    variableMap.get(id);
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

  @Override
  public Object[] toArray() {
    throw new NotImplementedException();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    throw new NotImplementedException();
  }

  @Override
  public boolean add(Variable variable) {
    int oldSize = size();
    put(variable);
    return oldSize != size();
  }

  @Override
  public boolean remove(Object o) {
    return variableMap.remove(((Variable) o).getId()) != null;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object variable : c) {
      if (!contains(variable)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends Variable> c) {
    int oldSize = size();
    for (Variable variable : c) {
      add(variable);
    }
    return oldSize != size();
  }


  @Override
  public boolean removeAll(Collection<?> c) {
    int oldSize = size();
    for (Object var : c) {
      remove(var);
    }
    return size() != oldSize;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {
    variableMap.clear();
  }

  public int size() {
    return variableMap.size();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean contains(Object o) {
    return variableMap.containsValue(o);
  }
}
