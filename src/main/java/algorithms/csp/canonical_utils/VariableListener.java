package algorithms.csp.canonical_utils;

/**
 * Created by Patrick on 15.09.2014.
 */
public interface VariableListener<T> {

  void onValueChanged(T value, int size);

  void onAssumptionMade(T value);

  void onDomainChanged(Domain<T> domain);

  boolean isEmpty();

  Integer getInitialValue();
}
