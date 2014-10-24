package algorithms.csp.canonical_utils;

/**
 * Created by Patrick on 15.09.2014.
 */
public interface VariableListener<T> {

  void onValueChanged(T value, int domainSize, Variable<T> variable);

  void onAssumptionMade(T value, Variable<T> variable);

  void onDomainChanged(Domain<T> domain, Variable<T> variable);

  boolean isEmpty();

  Integer getInitialValue();
}
