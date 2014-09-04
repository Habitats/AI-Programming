package algorithms.csp;

import java.util.HashMap;
import java.util.Map;

import ai.Log;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Evaluator {

  private static final String TAG = Evaluator.class.getSimpleName();

  private boolean evaluateExpression(Map<String, Variable> variables, String expression) {
    Function function = new Function().setVariables(variables).setExpression(expression);
    return function.call(variables);
  }

  public void test() {
    Variable x = new Variable("x", new Domain(1, 3, 4, 2));
    Variable y = new Variable("y", new Domain(2, 4, 2));
    Variable z = new Variable("z", new Domain(2, 3, 4));
    Variable a = new Variable("b", new Domain(2, 3, 4, 20, 30, 40));
    Variable b = new Variable("a", new Domain(2, 3, 4, -1, -2, -5));
    z.setValue(1);
    x.setValue(1);
    y.setValue(1);
    a.setValue(3);
    b.setValue(6);
    HashMap<String, Variable> variables = new HashMap<String, Variable>();
    variables.put(x.getId(), x);
    variables.put(y.getId(), y);
    variables.put(z.getId(), z);
    variables.put(a.getId(), a);
    variables.put(b.getId(), b);

    String expression = "(x == y < z) or z == 4 and b < 0";

    Log.v(TAG, evaluateExpression(variables, expression));
    y.setValue(2);
    Log.v(TAG, evaluateExpression(variables, expression));

    for (Integer zv : z.getDomain()) {
      for (Integer xv : x.getDomain()) {
        for (Integer yv : y.getDomain()) {
          for (Integer av : a.getDomain()) {
            for (Integer bv : b.getDomain()) {
              z.setValue(zv);
              x.setValue(xv);
              y.setValue(yv);
              a.setValue(av);
              b.setValue(bv);
              boolean ans = evaluateExpression(variables, expression);
              if (ans) {
                Log.v(TAG, x + ", " + y + ", " + z + ", " + ans);
              }
            }
          }
        }
      }
    }
  }
}
