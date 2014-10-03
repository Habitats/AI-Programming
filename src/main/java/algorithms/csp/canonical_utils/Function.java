package algorithms.csp.canonical_utils;

import org.python.core.PyBoolean;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.jsr223.PyScriptEngineFactory;

import java.util.Collection;
import java.util.HashMap;
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
  private static ScriptEngine engine = new PyScriptEngineFactory().getScriptEngine();

  private final Map<String, Variable> variablesMap;
  private final String expression;
  private final PyFunction lambda;

  public Function(HashMap<String, Variable> variablesMap, String expression) {
    this.variablesMap = new HashMap<>();
    for (Variable var : variablesMap.values()) {
      this.variablesMap.put(var.getId(), var.copy());
    }

    // put expression
    expression = setExpressionValues(expression, this.variablesMap.values());
    lambda = generateLambda(expression);
    this.expression = expression;
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

  private PyFunction generateLambda(String expression) {

    String lambdaString = "(lambda " + getKeys() + ": " + expression + ")";
//    Log.v(TAG, "generating " + lambdaString);
    PyFunction lambda = null;
    try {
      lambda = (PyFunction) engine.eval(lambdaString);
    } catch (ScriptException e) {
    }
    return lambda;
  }

  private String getKeys() {
    String keys = "";
    for (String key : variablesMap.keySet()) {
      keys += "," + key;
    }
    keys = keys.substring(1);
    return keys;
  }

  public boolean call(List<Variable> variables, Variable focalVariable) {

    // put all the right values in the valuesMap
    variablesMap.put(focalVariable.getId(), focalVariable.copy());
    for (Variable var : variables) {
      variablesMap.put(var.getId(), var.copy());
    }
    return call(variablesMap);
  }

  public boolean call(VariableList variables) {
    for (Variable var : variables) {
      if (variablesMap.containsKey(var.getId())) {
        variablesMap.put(var.getId(), var.copy());
      }
    }
    return call(variablesMap);
  }

  private boolean call(Map<String, Variable> variablesMap) {
    // put the values in the right order according to how the parameters for the lambda was created
    PyInteger[] args = new PyInteger[variablesMap.size()];
    int i = 0;
    for (String key : variablesMap.keySet()) {
      args[i++] = new PyInteger(variablesMap.get(key).getValue());
    }

    // call the python lambda with args: x = 1, y = 2 etc, order is important
//    Log.v(TAG, "calling " + lambdaString + " values: " + getVariableValues());
    PyObject ans = lambda.__call__(args);

    return ((PyBoolean) ans).getBooleanValue();
  }

  public String getVariableValues() {
    String variableValues = "";
    for (Variable var : variablesMap.values()) {
      variableValues += ", " + var.getId() + "=" + var.getValue();
    }
    return variableValues.substring(2);
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
