package algorithms.csp.canonical_utils;

import java.io.Serializable;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Variable implements Comparable<Variable>, Serializable {

  private final String id;
  private Domain domain;
  private int value;
  private boolean hasValue;
  private VariableListener listener;
  private boolean assumption = false;

  public Variable(String id, Domain domain) {
    this.id = id;
    this.domain = domain;
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
    for (Integer val : domain) {
      if (val != value) {
        domain.remove(val);
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
    return var;
  }

  @Override
  public boolean equals(Object obj) {
    Variable other = (Variable) obj;
    if (!id.equals(id)) {
      return false;
    }
    if (!domain.equals(other.getDomain())) {
      return false;
    }
    if (hasValue != other.hasValue) {
      return false;
    }
    if (listener != other.listener) {
      return false;
    }
    if (value != other.value) {
      return false;
    }
    return true;
  }

  public void update() {
    setValue(getValue());
    if (assumption) {
      setAssumption(value);
    }
  }
}
