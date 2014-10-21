package algorithms.csp.canonical_utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Variable<T> implements Comparable<Variable<T>>, VariableListener<T> {

  private String id;
  private Domain<T> domain;
  private T value;
  private boolean hasValue;
  private List<VariableListener> listeners;
  private boolean assumption = false;
  private List<Constraint<T>> constraintsContainingVariable;
  private Set<String> variableIDsInConstraintsContainingVariable;

  public Variable(String id, Domain<T> domain) {
    this.id = id;
    this.domain = domain;
    listeners = new ArrayList<>();
  }

  public Set<String> getVariableIDsInConstraintsContainingVariable() {
    return variableIDsInConstraintsContainingVariable;
  }

  public void setConstraintsContainingVariable(List<Constraint> constraints) {
    this.constraintsContainingVariable = new ArrayList<>();
    this.variableIDsInConstraintsContainingVariable = new HashSet<>();
    for (Constraint constraint : constraints) {
      if (constraint.contains(this)) {
        this.constraintsContainingVariable.add(constraint);
      }
    }
    for (Constraint<T> constraint : constraintsContainingVariable) {
      for (Variable<T> variable : constraint.getVariables()) {
        if (variable.getId().equals(getId())) {
          continue;
        }
        variableIDsInConstraintsContainingVariable.add(variable.getId());
      }
    }
  }


  public List<Constraint<T>> getConstraintsContainingVariable() {
    return constraintsContainingVariable;
  }

  public Variable<T> setValue(T value) {
    this.value = value;
    hasValue = true;
    fireValueChanged(value);
    fireDomainChanged();
    return this;
  }

  private void fireDomainChanged() {
    for (VariableListener listener : listeners) {
      listener.onDomainChanged(domain);
    }
  }

  private void fireValueChanged(T value) {
    for (VariableListener listener : listeners) {
      listener.onValueChanged(value, domain.getSize());
    }
  }

  private void fireAssumptionMade(T value) {
    for (VariableListener listener : listeners) {
      listener.onAssumptionMade(value);
    }
  }

  public Variable<T> setAssumption(T value) {
    this.value = value;
    assumption = true;
    Iterator<T> iterator = domain.iterator();
    while (iterator.hasNext()) {
      T val = iterator.next();
      if (!(val).equals(value)) {
        iterator.remove();
        fireDomainChanged();
      }
    }

    fireAssumptionMade(value);
    return this;
  }

  public T getValue() {
    return value;
  }

  public Domain<T> getDomain() {
    return domain;
  }

  public String getId() {
    return id;
  }

  public void clear() {
    hasValue = false;
  }

  public boolean hasValue() {
    return hasValue;
  }

  @Override
  public String toString() {
    return getId() + "=" + (hasValue ? getValue() : "none") //
           + " " + getDomain() //
        ;
  }

  @Override
  public int compareTo(Variable<T> o) {
    return String.CASE_INSENSITIVE_ORDER.compare(getId(), o.getId());
  }

  public void addListener(VariableListener listener) {
    this.listeners.add(listener);
  }

  public Variable<T> copy() {
    Variable<T> var = new Variable(id, domain.copy());
    var.value = value;
    var.hasValue = hasValue;
    var.listeners = listeners;
    var.constraintsContainingVariable = constraintsContainingVariable;
    var.variableIDsInConstraintsContainingVariable = variableIDsInConstraintsContainingVariable;
    return var;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Variable) {
      Variable other = (Variable) obj;
      return (!id.equals(other.id))  //
             && (!domain.equals(other.domain))  //
             && (hasValue != other.hasValue) //
             && (listeners != other.listeners)  //
             && (value != other.value)  //
          ;
    }
    return false;
  }

  public void update() {
    setValue(getValue());
    if (assumption) {
      setAssumption(value);
    }
  }


  @Override
  public void onValueChanged(T value, int size) {
    setValue(value);
  }

  @Override
  public void onAssumptionMade(T value) {
    setAssumption(value);
  }

  @Override
  public void onDomainChanged(Domain<T> domain) {
    // do nothing
  }

  @Override
  public boolean isEmpty() {
    // do nothing
    return domain.iEmpty();
  }

  @Override
  public Integer getInitialValue() {
    // do nothing
    return 0;
  }

  public void dupe(Variable<T> outputVariable) {
    this.domain = outputVariable.domain;
    this.value = outputVariable.value;
  }
}
