package puzzles.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 01.10.2014.
 */
public class FlowUtils {

  public static final List<String> samples = new ArrayList<>();

  static {
    samples.add("3 2 \n" + "0 0 0 2 1  \n" + "1 2 2 1 1  ");
    samples.add("6 6 \n" + "0 2 2 4 5  \n" + "1 0 1 0 5  \n" + "2 0 0 1 5  \n" + "3 2 0 5 5  \n" + "4 2 3 2 5  \n"
                + "5 2 1 4 4  \n");
    samples.add("6 5 \n" + "0 3 0 3 2  \n" + "1 1 0 2 4  \n" + "2 0 0 2 0  \n" + "3 4 4 5 0  \n" + "4 4 5 5 1  \n");
    samples.add("8 9 \n" + "0 0 7 3 7  \n" + "1 1 1 2 5  \n" + "2 2 6 3 2  \n" + "3 2 4 3 1  \n" + "4 3 6 4 3  \n"
                + "5 4 7 7 1  \n" + "6 4 6 6 6  \n" + "7 4 4 6 1  \n" + "8 5 4 7 0  \n");
    samples.add("9 8 \n" + "0 0 5 7 8  \n" + "1 0 8 7 5  \n" + "2 1 1 1 3  \n" + "3 2 0 4 3  \n" + "4 2 1 3 5  \n"
                + "5 3 0 3 4  \n" + "6 5 1 7 4  \n" + "7 5 3 5 5  \n");
    samples.add("5 4 \n" + "0 0 4 2 3  \n" + "1 1 0 1 3  \n" + "2 1 1 4 2  \n" + "3 3 1 4 3  \n");
    samples.add("10 11 \n" + "0 0 0 1 8  \n" + "1 0 1 1 3  \n" + "2 1 4 6 7  \n" + "3 2 1 7 0  \n" + "4 2 6 5 8  \n"
                + "5 3 4 8 3  \n" + "6 4 2 4 6  \n" + "7 4 5 5 7  \n" + "8 5 2 8 0  \n" + "9 5 3 6 6  \n"
                + "10 6 8 8 6  \n");
    samples.add("10 9 \n" + "0 0 1 6 9  \n" + "1 1 1 2 8  \n" + "2 1 2 3 4  \n" + "3 1 4 4 4  \n" + "4 1 8 3 7  \n"
                + "5 2 2 4 7  \n" + "6 2 6 5 9  \n" + "7 3 6 5 4  \n" + "8 7 9 8 0  \n");
  }
}
