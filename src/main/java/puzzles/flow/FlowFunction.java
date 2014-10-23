package puzzles.flow;

import java.util.HashMap;

import algorithms.csp.canonical_utils.Function;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.10.2014.
 */
public class FlowFunction extends Function {

  public FlowFunction(HashMap<String, Variable<Integer>> variablesMap, String expression) {
    super(variablesMap, expression);
  }
}
