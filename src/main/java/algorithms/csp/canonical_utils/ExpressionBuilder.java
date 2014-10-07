package algorithms.csp.canonical_utils;

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
    return S + tuple.getValue(0) + IS + tuple.getValue(1) + E;
  }

  public static String not(Tuple tuple) {
    return S + tuple.getValue(0) + NOT + tuple.getValue(1) + E;
  }

  public static String atLeastTwoTuplesEquals(Tuple[] pairs) {
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
      //    ((xy == xy1) and (xy == xy2))
      // or ((xy == xy1) and (xy == xy3))
      // or ((xy == xy2) and (xy == xy3))
      expression = is(xy1) + AND + is(xy2) //
                   + OR + is(xy1) + AND + is(xy3) //
                   + OR + is(xy2) + AND + is(xy3) //
      ;
      return expression;

    } else if (pairs.length == 4) {
      xy1 = pairs[0];
      xy2 = pairs[1];
      xy3 = pairs[2];
      xy4 = pairs[3];
      //    ((xy == xy1) and (xy == xy2))
      // or ((xy == xy1) and (xy == xy3))
      // or ((xy == xy1) and (xy == xy4))
      // or ((xy == xy2) and (xy == xy3))
      // or ((xy == xy2) and (xy == xy4))
      // or ((xy == xy3) and (xy == xy4))
      expression = is(xy1) + AND + is(xy2) //
                   + OR + is(xy1) + AND + is(xy3)//
                   + OR + is(xy1) + AND + is(xy4) //
                   + OR + is(xy2) + AND + is(xy3) //
                   + OR + is(xy2) + AND + is(xy4) //
                   + OR + is(xy3) + AND + is(xy4) //
      ;
      return expression;
    }
    return "";
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

  public static String atLeastOneTupleEquals(Tuple[] pairs) {
    String expression = "";
    for (int i = 0; i < pairs.length; i++) {
      //    (xy == xy1)
      // or (xy == xy2)
      // or (xy == xy3)
      // or ...
      expression += OR + S + is(pairs[i]) + E;
    }
    expression = expression.substring(3);
    return expression;
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

      return expression;
    }
    return "";
  }
}
