package algorithms.csp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Domain implements Iterable<Integer> {

  private final List<Integer> args;

  public Domain(int... args) {
    ArrayList<Integer> lst = new ArrayList<Integer>();
    for (int i = 0; i < args.length; i++) {
      lst.add(args[i]);
    }
    this.args = lst;
  }

  public Domain(List<Integer> args) {
    this.args = args;
  }

  @Override
  public Iterator<Integer> iterator() {
    return args.iterator();
  }
}
