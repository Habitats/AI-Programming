package algorithms.csp.canonical_utils;

/**
 * Created by Patrick on 15.09.2014.
 */
public interface VariableListener {

  void onValueChanged(int value, int size);

  void onAssumptionMade(int value);
  void onDomainChanged(Domain domain);
}
