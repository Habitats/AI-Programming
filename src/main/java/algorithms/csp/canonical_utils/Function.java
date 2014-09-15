package algorithms.csp.canonical_utils;

import org.python.core.PyBoolean;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.jsr223.PyScriptEngineFactory;

import java.util.Collection;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import ai.Log;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Function {

  private static final String TAG = Function.class.getSimpleName();
  private PyInteger[] args;
  private PyFunction lambda;
  private static ScriptEngine engine = new PyScriptEngineFactory().getScriptEngine();
  private Map<String, Variable> variables;
  private String expression;
  private String lambdaString;

  public Function setVariables(Map<String, Variable> variables) {
    this.variables = variables;
    return this;
  }

  private String setExpressionValues(String expression, Collection<Variable> variables) {
    for (Variable var : variables) {
      if (var.hasValue()) {
        expression = expression.replaceAll(var.getId(), String.valueOf(var.getValue()));
        Log.v(TAG, "modifying expression: " + expression);
      }
    }
    return expression;
  }

  public Function setExpression(String expression) {
    expression = setExpressionValues(expression, variables.values());
    lambda = generateLambda(expression);
    this.expression = expression;
    return this;
  }

  private PyFunction generateLambda(String expression) {

    lambdaString = "(lambda " + getKeys() + ": " + expression + ")";
    Log.v(TAG, "generating " + lambdaString);
    try {
      this.lambda = (PyFunction) engine.eval(lambdaString);
    } catch (ScriptException e) {
    }
    return this.lambda;
  }

  private String getKeys() {
    String keys = "";
    for (String key : variables.keySet()) {
//      if (!variables.get(key).hasValue()) {
//      }
      keys += "," + key;
    }
    keys = keys.substring(1);
    return keys;
  }

  public boolean call() {
    args = new PyInteger[variables.size()];
    int i = 0;
    for (Variable var : variables.values()) {
      args[i++] = new PyInteger(var.getValue());
    }

    // call the python lambda with args: x = 1, y = 2 etc, order is important
    Log.v(TAG, "calling " + lambdaString + " function: " + toString());
    PyObject ans = lambda.__call__(this.args);


    return ((PyBoolean)ans).getBooleanValue();
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

  public boolean contains(Variable x) {
    return variables.containsKey(x.getId());
  }

  public Map<String, Variable> getVariables() {
    return variables;
  }
}
