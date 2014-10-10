package algorithms.csp.canonical_utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Variable implements Comparable<Variable> {

  private final String id;
  private final Domain domain;
  private int value;
  private boolean hasValue;
  private VariableListener listener;
  private boolean assumption = false;
  private List<Constraint> constraintsContainingVariable;
  private Set<String> variableIDsInConstraintsContainingVariable;

  public Variable(String id, Domain domain) {
    this.id = id;
    this.domain = domain;
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
    for (Constraint constraint : constraintsContainingVariable) {
      for (Variable variable : constraint.getVariables()) {
        if (variable.getId().equals(getId())) {
          continue;
        }
        variableIDsInConstraintsContainingVariable.add(variable.getId());
      }
    }
  }


  public List<Constraint> getConstraintsContainingVariable() {
    return constraintsContainingVariable;
  }

  public Variable setValue(int value) {
    this.value = value;
    hasValue = true;
    if (listener != null) {
      getListener().onValueChanged(value, domain.getSize());
      getListener().onDomainChanged(domain);
    }
    return this;
  }

  public Variable setAssumption(int value) {
    this.value = value;
    assumption = true;
    Iterator<Integer> iterator = domain.iterator();
    while (iterator.hasNext()) {
      Integer val = iterator.next();
      if ((val) != value) {
        iterator.remove();
        if (listener != null) {
          listener.onDomainChanged(domain);
        }
      }
    }

    if (listener != null) {
      getListener().onAssumptionMade(value);
    }
    return this;
  }

  public VariableListener getListener() {
    return listener;
  }


  public int getValue() {
    return value;
  }

  public Domain getDomain() {
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
  public int compareTo(Variable o) {
    return 0;
  }

  public void setListener(VariableListener listener) {
    this.listener = listener;
  }

  public Variable copy() {
    Variable var = new Variable(id, domain.copy());
    var.value = value;
    var.hasValue = hasValue;
    var.listener = listener;
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
             && (listener != other.listener)  //
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
}
