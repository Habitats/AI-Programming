package algorithms.csp;

import org.python.core.PyBoolean;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.jsr223.PyScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by Patrick on 04.09.2014.
 */
public class CSP {

  public CSP() {
  }

  public static boolean Parse(int x, int y) throws ScriptException {
    PyScriptEngineFactory factory = new PyScriptEngineFactory();
    ScriptEngine engine = factory.getScriptEngine();
    PyFunction function = (PyFunction) engine.eval("x < y");
    PyBoolean ans = (PyBoolean) function.__call__(new PyInteger(x), new PyInteger(y));
    return ans.getBooleanValue();
  }

  public void test() {
    new Evaluator().test();
  }
}
