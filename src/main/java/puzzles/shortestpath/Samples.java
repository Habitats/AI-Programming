package puzzles.shortestpath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 26.08.2014.
 */
public class Samples {


  private static final List<String> astarSamples = new ArrayList<>();

  static {
    astarSamples.add("(6,6)(1,0) (5,5)(3,2,2,2)(0,3,1,3)(2,0,4,2)(2,5,2,1)");
    astarSamples.add("(10, 10) (0, 0) (9, 9) (2, 3, 5, 5) (8, 8, 2, 1)");
    astarSamples.add("(20, 20) (19, 3) (2, 18) (5, 5, 10, 10) (1, 2, 4, 1)");
    astarSamples
        .add("(20, 20) (0, 0) (19, 19) (17, 10, 2, 1) (14, 4, 5, 2) (3, 16, 10, 2) (13, 7, 5, 3) (15, 15, 3, 3)");
    astarSamples.add("(10, 10) (0, 0) (9, 5) (3, 0, 2, 7) (6, 0, 4, 4) (6, 6, 2, 4)");
    astarSamples.add("(20, 20) (0, 0) (19, 13) (4, 0, 4, 16) (12, 4, 2, 16) (16, 8, 4, 4)");
    astarSamples.add(
        "(400, 400)\n" + "(20, 20)\n" + "(300, 300)\n" + "(4, 0, 4, 16)\n" + "(12, 4, 2, 16)\n" + "(16, 8, 4, 4)\n"
        + "(60,60, 200,100)\n" + "(60,60, 100,200)");
    astarSamples.add(
        "(100, 100)\n" + "(0, 0)\n" + "(90, 99)\n" + "(4, 1, 10, 90)\n" + "(20, 1, 60, 10)\n" + "(20, 40, 80, 10)\n"
        + "(20, 60, 79, 10)\n" + "(10, 20, 35, 10)\n" + "(80,60, 1, 40)\n" + "(12, 4, 2, 16)\n" + "(16, 8, 4, 4)\n");
    astarSamples.add("(8, 6)\n" + "(2, 3)\n" + "(6, 3)\n" + "(4,2 , 1, 3)");
    astarSamples.add(
        "30 30\n" + "0 15 29 15\n" + "5 8 10 3 \n" + "7 4 15 2 \n" + "15 8 3 12 \n" + "22 4 2 22 \n" + "5 20 10 4 \n"
        + "8 28 17 2 \n" + "17 21 4 6");
    astarSamples.add("((20 20) (0 10) (19 10) (4 2 13 3) (4 16 13 3) (17 2 2 15))) ");
  }

  public static String withNewLine(String s) {
    s = s.replaceAll("\\) \\(", "\\)\\(");
    return s.replaceAll("\\)\\(", "\\)\n\\(");
  }

  public static String getAstarSample(int i) {
    return withNewLine(astarSamples.get(i));
  }
}
