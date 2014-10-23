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



  public static String exatlyOneTupleEquals(Tuple[] pairs) {
    String expression;
    Tuple xy1, xy2, xy3, xy4;
    if (pairs.length == 1) {
      // xy == xy1
      xy1 = pairs[0];
      return is(xy1);
    } else if (pairs.length == 2) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      //    ((xy1 != xy2) and (xy1 == xy2))
      // or ((xy1 == xy2) and (xy1 != xy2))
      expression = not(xy1) + AND + is(xy2)  //
                   + OR + is(xy1) + AND + not(xy2) //
      ;
      return expression;
    } else if (pairs.length == 3) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      xy3 = pairs[2];
      //    ((xy1 != xy2) and (xy1 != xy3) and (xy1 == xy3))
      // or ((xy1 != xy2) and (xy1 == xy3) and (xy1 != xy3))
      // or ((xy1 == xy2) and (xy1 != xy3) and (xy1 != xy3))
      expression = not(xy1) + AND + not(xy2) + AND + is(xy3)  //
                   + OR + not(xy1) + AND + is(xy2) + AND + not(xy3) //
                   + OR + is(xy1) + AND + not(xy2) + AND + not(xy3) //
      ;
      return expression;

    } else if (pairs.length == 4) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      xy3 = pairs[2];
      xy4 = pairs[3];
      //    ((xy1 != xy2) and (xy1 != xy3) and (xy1 != xy3) and (xy1 == xy4))
      // or ((xy1 != xy2) and (xy1 != xy3) and (xy1 == xy3) and (xy1 != xy4))
      // or ((xy1 != xy2) and (xy1 == xy3) and (xy1 != xy3) and (xy1 != xy4))
      // or ((xy1 == xy2) and (xy1 != xy3) and (xy1 != xy3) and (xy1 != xy4))
      expression = //
          not(xy1) + AND + not(xy2) + AND + not(xy3) + AND + is(xy4)   //
          + OR + not(xy1) + AND + not(xy2) + AND + is(xy3) + AND + not(xy4)   //
          + OR + not(xy1) + AND + is(xy2) + AND + not(xy3) + AND + not(xy4)   //
          + OR + is(xy1) + AND + not(xy2) + AND + not(xy3) + AND + not(xy4)   //
      ;
      return expression;
    }
    return "";
  }

  public static String exatlyTwoTuplesEquals(Tuple[] pairs) {
    String expression;
    Tuple xy1, xy2, xy3, xy4;
    if (pairs.length == 2) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      //    ((xy == xy1) and (xy == xy2)
      return is(xy1) + AND + is(xy2);

    } else if (pairs.length == 3) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      xy3 = pairs[2];
      //    ((xy != xy1) and (xy == xy2) and (xy == xy3))
      // or ((xy == xy1) and (xy != xy2) and (xy == xy3))
      // or ((xy == xy1) and (xy == xy2) and (xy != xy3))
      expression = not(xy1) + AND + is(xy2) + AND + is(xy3) //
                   + OR + is(xy1) + AND + not(xy2) + AND + is(xy3) //
                   + OR + is(xy1) + AND + is(xy2) + AND + not(xy3) //
      ;

      // ((NOT w) OR  (NOT y) OR  (NOT z)) AND  (w OR  y) AND  (w OR  z) AND  (y OR  z)
      // w = (xy == xy1)
      // y = (xy == xy2)
      // z = (xy == xy3)
      // q = (xy == xy4)
//      expression = S + S + not(xy1) + E + OR + S + not(xy2) + E + OR + S + not(xy3) + E + E  //
//                   + AND + S + is(xy1) + OR + is(xy2) + E  //
//                   + AND + S + is(xy1) + OR + is(xy3) + E  //
//                   + AND + S + is(xy2) + OR + is(xy3) + E;

      return expression;

    } else if (pairs.length == 4) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      xy3 = pairs[2];
      xy4 = pairs[3];

      //    ((xy == xy1) and (xy == xy2) and (xy != xy3) and (xy != xy4))
      // or ((xy == xy1) and (xy != xy2) and (xy == xy3) and (xy != xy4))
      // or ((xy == xy1) and (xy != xy2) and (xy != xy3) and (xy == xy4))
      // or ((xy != xy1) and (xy == xy2) and (xy == xy3) and (xy != xy4))
      // or ((xy != xy1) and (xy == xy2) and (xy != xy3) and (xy == xy4))
      // or ((xy != xy1) and (xy != xy2) and (xy == xy3) and (xy == xy4))
      expression = is(xy1) + AND + is(xy2) + AND + not(xy3) + AND + not(xy4) //
                   + OR + is(xy1) + AND + not(xy2) + AND + is(xy3) + AND + not(xy4)//
                   + OR + is(xy1) + AND + not(xy2) + AND + not(xy3) + AND + is(xy4)//
                   + OR + not(xy1) + AND + is(xy2) + AND + is(xy3) + AND + not(xy4)//
                   + OR + not(xy1) + AND + is(xy2) + AND + not(xy3) + AND + is(xy4)//
                   + OR + not(xy1) + AND + not(xy2) + AND + is(xy3) + AND + is(xy4)//
      ;

//      ((NOT q) OR  (NOT w) OR  (NOT x))
//       AND  ((NOT q) OR  (NOT w) OR  (NOT y))
//      AND  ((NOT q) OR  (NOT x) OR  (NOT y))
//      AND  (q OR  w OR  x)
//      AND  (q OR  w OR  y)
//      AND  (q OR  x OR  y)
//      AND  ((NOT w) OR  (NOT x) OR  (NOT y))
//      AND  (w OR  x OR  y)

      // w = (xy == xy1)
      // y = (xy == xy2)
      // z = (xy == xy3)
      // q = (xy == xy4)
//
//      expression =  //
//          S + S + not(xy1) + E + OR + S + not(xy2) + E + OR + S + not(xy3) + E + E + AND + S + S + not(xy1) + E + OR + S
//          + not(xy2) + E + OR + S + not(xy4) + E + E + AND + S + S + not(xy1) + E + OR + S + not(xy3) + E + OR + S
//          + not(xy4) + E + E + AND + S + is(xy1) + OR + is(xy2) + OR + is(xy3) + E + AND + S + is(xy1) + OR + is(xy2)
//          + OR + is(xy4) + E + AND + S + is(xy1) + OR + is(xy3) + OR + is(xy4) + E + AND + S + S + not(xy2) + E + OR + S
//          + not(xy3) + E + OR + S + not(xy4) + E + E + AND + S + is(xy2) + OR + is(xy3) + OR + is(xy4) + E +
//          " ";

      return expression;
    }
    return "";
  }

}
