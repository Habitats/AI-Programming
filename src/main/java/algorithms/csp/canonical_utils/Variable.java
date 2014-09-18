package algorithms.csp.canonical_utils;

import java.io.Serializable;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Variable implements Comparable<Variable>, Serializable {

  private final String id;
  private final Domain domain;
  private int value;
  private boolean hasValue;
  private VariableListener listener;

  public Variable(String id, Domain domain) {
    this.id = id;
    this.domain = domain;
  }

  public Variable setValue(int value) {
    this.value = value;
    hasValue = true;
    getListener().onValueChanged(value);
    return this;
  }

  public Variable setAssumption(int value) {
    //TODO: this can't remove from domain, since empty domain == bad!
    this.value = value;
    for (Integer val : domain) {
      if (val != value) {
        domain.remove(val);
        listener.onDomainChanged(domain);
      }
    }
    getListener().onAssumptionMade(value);
    return this;
  }

  public VariableListener getListener() {
    if (listener == null) {
      throw new IllegalArgumentException(VariableListener.class.getSimpleName() + " may not be null!");
    }
    return listener;
  }

  public void copyDomain(Variable other) {
    this.domain.setDomain(other.domain);
    listener.onDomainChanged(this.domain);
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

}
