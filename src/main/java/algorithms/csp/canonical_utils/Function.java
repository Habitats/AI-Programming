package algorithms.csp.canonical_utils;

import org.python.core.PyBoolean;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.jsr223.PyScriptEngineFactory;

import java.util.Collection;
import java.util.List;
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
  private Map<String, Variable> variablesMap;
  private String expression;
  private String lambdaString;

  public Function setVariablesMap(Map<String, Variable> variablesMap) {
    this.variablesMap = variablesMap;
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
    expression = setExpressionValues(expression, variablesMap.values());
    lambda = generateLambda(expression);
    this.expression = expression;
    return this;
  }

  private PyFunction generateLambda(String expression) {

    lambdaString = "(lambda " + getKeys() + ": " + expression + ")";
//    Log.v(TAG, "generating " + lambdaString);
    try {
      this.lambda = (PyFunction) engine.eval(lambdaString);
    } catch (ScriptException e) {
    }
    return this.lambda;
  }

  private String getKeys() {
    String keys = "";
    for (String key : variablesMap.keySet()) {
//      if (!variablesMap.get(key).hasValue()) {
//      }
      keys += "," + key;
    }
    keys = keys.substring(1);
    return keys;
  }

  public boolean call(List<Variable> variables, Variable focalVariable) {

    // set all the right values in the valuesMap
    variablesMap.get(focalVariable.getId()).setValue(focalVariable.getValue());
    for (Variable var : variables) {
      variablesMap.get(var.getId()).setValue(var.getValue());
    }

    // put the values in the right order according to how the parameters for the lambda was created
    args = new PyInteger[variables.size() + 1];
    int i = 0;
    for(String key: variablesMap.keySet()){
      args[i++] = new PyInteger(variablesMap.get(key).getValue());
    }

    // call the python lambda with args: x = 1, y = 2 etc, order is important
//    Log.v(TAG, "calling " + lambdaString + " values: " + getVariableValues());
    PyObject ans = lambda.__call__(this.args);

    return ((PyBoolean) ans).getBooleanValue();
  }

  public String getVariableValues() {
    String vals = "";
    if (args != null) {
      for (PyInteger var : args) {
        vals += var.toString() + ", ";
      }
    }
    return vals;
  }

  @Override
  public String toString() {
    return "Values: " + getVariableValues() + " Expression: " + expression;
  }

  public boolean contains(Variable x) {
    return variablesMap.containsKey(x.getId());
  }

  public Map<String, Variable> getVariablesMap() {
    return variablesMap;
  }
}
