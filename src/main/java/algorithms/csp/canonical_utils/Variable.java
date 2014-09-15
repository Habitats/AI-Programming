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
    listener.onValueChanged(value);
    return this;
  }

  public Variable setAssumption(int value) {
    this.value = value;
    for (Integer val : domain) {
      if (val != value) {
        domain.remove(val);
      }
    }
    listener.onAssumptionMade(value);
    return this;
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