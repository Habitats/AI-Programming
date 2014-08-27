package shortestpath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 26.08.2014.
 */
public class Samples {


  private static List<String> astarSamples = new ArrayList<String>();

  static {
    astarSamples.add("(6,6)(1,0) (5,5)(3,2,2,2)(0,3,1,3)(2,0,4,2)(2,5,2,1)");
    astarSamples.add("(10, 10) (0, 0) (9, 9) (2, 3, 5, 5) (8, 8, 2, 1)");
    astarSamples.add("(20, 20) (19, 3) (2, 18) (5, 5, 10, 10) (1, 2, 4, 1)");
    astarSamples
        .add("(20, 20) (0, 0) (19, 19) (17, 10, 2, 1) (14, 4, 5, 2) (3, 16, 10, 2) (13, 7, 5, 3) (15, 15, 3, 3)");
    astarSamples.add("(10, 10) (0, 0) (9, 5) (3, 0, 2, 7) (6, 0, 4, 4) (6, 6, 2, 4)");
    astarSamples.add("(20, 20) (0, 0) (19, 13) (4, 0, 4, 16) (12, 4, 2, 16) (16, 8, 4, 4)");
  }

  public static String withNewLine(String s) {
    s = s.replaceAll("\\) \\(", "\\)\\(");
    return s.replaceAll("\\)\\(", "\\)\n\\(");
  }

  public static String getAstarSample(int i) {
    return withNewLine(astarSamples.get(i));
  }
}