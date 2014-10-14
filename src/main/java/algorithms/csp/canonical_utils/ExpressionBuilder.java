package algorithms.csp.canonical_utils;

import org.javatuples.Pair;
import org.javatuples.Tuple;

/**
 * Created by Patrick on 07.10.2014.
 */
public class ExpressionBuilder {

  public static final String OP = "op";
  public static final String AND = " and ";
  public static final String E = ")";
  public static final String S = "(";
  public static final String OR = " or ";
  public static final String IS = " == ";
  public static final String NOT = " != ";

  public static String is(Tuple tuple) {
    tuple = sortTuple(tuple);
    return S + tuple.getValue(0) + IS + tuple.getValue(1) + E;
  }

  private static Tuple sortTuple(Tuple tuple) {
    String x = tuple.getValue(0).toString();
    String y = tuple.getValue(1).toString();
    int res = String.CASE_INSENSITIVE_ORDER.compare(x, y);

    // remove copy constraints, but first we need to order the id's
    // duplication is handled automatically by using a hashmap on the Id
    String id1 = res < 0 ? x : y;
    String id2 = id1 == x ? y : x;
    tuple = Pair.with(id1, id2);
    return tuple;
  }

  public static String not(Tuple tuple) {
    tuple = sortTuple(tuple);
    return S + tuple.getValue(0) + NOT + tuple.getValue(1) + E;
  }
}
