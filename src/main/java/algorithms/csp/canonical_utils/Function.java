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
import algorithms.a_star_csp.SimpleAStarCspPuzzle;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Function {

  private static final String TAG = Function.class.getSimpleName();
  private static final ScriptEngine engine = new PyScriptEngineFactory().getScriptEngine();

  private final Map<String, Variable<Integer>> variablesMap;
  private final String expression;
  private final PyFunction lambda;
  private final Map<String, Boolean> history;

  public Function(HashMap<String, Variable<Integer>> variablesMap, String expression) {
    this.variablesMap = new HashMap<>();
    history = new HashMap<>();
    for (Variable<Integer> var : variablesMap.values()) {
      this.variablesMap.put(var.getId(), var.copy());
    }

    // put expression
    expression = setExpressionValues(expression, this.variablesMap.values());
    lambda = generateLambda(expression);
    this.expression = expression;
  }

  private String setExpressionValues(String expression, Collection<Variable<Integer>> variables) {
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
      Log.i(TAG, "INVALID CALL TO EVAL: " + lambdaString);
      throw new IllegalArgumentException();
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

  public boolean call(List<Variable> variables, Variable<Integer> focalVariable) {

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

  private boolean call(Map<String, Variable<Integer>> variablesMap) {
    // put the values in the right order according to how the parameters for the lambda was created
    PyInteger[] args = new PyInteger[variablesMap.size()];
    int i = 0;
    for (String key : variablesMap.keySet()) {
      Variable<Integer> integerVariable = variablesMap.get(key);
      Integer value = integerVariable.getValue();
      if(value == null){
        Log.v(TAG, "wut");
        return false;
      }
      args[i++] = new PyInteger(value);
    }

    // call the python lambda with args: x = 1, y = 2 etc, order is important
//    Log.v(TAG, toString());
    if (history.containsKey(argsToString(args))) {
      SimpleAStarCspPuzzle.hit++;
      return history.get(argsToString(args));
    } else {
      try {
        PyObject ans = lambda.__call__(args);
        boolean booleanValue = ((PyBoolean) ans).getBooleanValue();
        history.put(argsToString(args), booleanValue);
        SimpleAStarCspPuzzle.miss++;
        return booleanValue;
      } catch (Exception e) {
        Log.v(TAG, "lambda crashed: " + toString(), e);
        throw new IllegalArgumentException();
      }
    }
  }

  private String argsToString(PyInteger[] args) {
    StringBuilder sb = new StringBuilder();
    for (PyInteger i : args) {
      sb.append(i.toString());
    }
    return sb.toString();
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

  public Map<String, Variable<Integer>> getVariablesMap() {
    return variablesMap;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Function && ((Function) obj).expression.equals(expression));
  }
}
