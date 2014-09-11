package algorithms.csp;

import org.python.core.PyBoolean;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.jsr223.PyScriptEngineFactory;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Function {

  private PyInteger[] args;
  private PyFunction function;
  private ScriptEngine engine;
  private PyScriptEngineFactory factory;
  private Map<String, Variable> variables;
  private String expression;

  public Function setVariables(Map<String, Variable> variables) {
    this.variables = variables;
    return this;
  }

  public Function setExpression(String expression) {
    factory = new PyScriptEngineFactory();
    this.expression = expression;
    engine = factory.getScriptEngine();

    String lambda = "(lambda " + getKeys() + ": " + expression + ")";
    try {
      function = (PyFunction) engine.eval(lambda);
    } catch (ScriptException e) {
    }
    return this;
  }

  private String getKeys() {
    String keys = "";
    for (String key : variables.keySet()) {
      if (!variables.get(key).hasValue()) {
        keys += "," + key;
      }
    }
    keys = keys.substring(1);
    return keys;
  }

  public boolean call(Map<String, Variable> variables) {
    args = new PyInteger[variables.size()];
    int i = 0;
    for (Variable var : variables.values()) {
      args[i++] = new PyInteger(var.getValue());
    }

    // call the python function with args: x = 1, y = 2 etc, order is important
    PyBoolean ans = (PyBoolean) function.__call__(this.args);
    return ans.getBooleanValue();
  }

  public String getVariableValues() {
    String vals = "";
    for (Variable var : variables.values()) {
      vals += var + ", ";
    }
    return vals;
  }

  @Override
  public String toString() {
    return "Values: " + getVariableValues() + " Expression: " + expression;
  }
}
