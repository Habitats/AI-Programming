package algorithms.csp.canonical_utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Domain implements Iterable<Integer>, Serializable {

  private static final String TAG = Domain.class.getSimpleName();
  private final List<Integer> args;

  public Domain(int... args) {
    List<Integer> lst = new CopyOnWriteArrayList<>();
    for (int i = 0; i < args.length; i++) {
      lst.add(args[i]);
    }
    this.args = lst;
  }

  public Domain(List<Integer> args) {
    this.args = new CopyOnWriteArrayList<>();
    this.args.addAll(args);
  }

  @Override
  public Iterator<Integer> iterator() {
    return args.iterator();
  }

  public void remove(Integer val) {
    args.remove(val);
  }

  public int getSize() {
    return args.size();
  }

  @Override
  public String toString() {
    if (args.size() <= 0) {
      return "Empty";
    }
    String domain = "";
    for (Integer i : args) {
      domain += ", " + i;
    }
    return "Domain: " + domain.substring(2);
  }

  public boolean iEmpty() {
    return args.isEmpty();
  }

  public void setDomain(Domain domain) {
    // create a copy, just to be sure
    List<Integer> args = new ArrayList<>();
    args.addAll(domain.args);

    this.args.clear();
    this.args.addAll(args);
  }

  public Domain copy() {
    return new Domain(args);
  }

  @Override
  public boolean equals(Object other) {
    for (int i = 0; i < args.size(); i++) {
      if (((Domain) other).args.get(i) != args.get(i)) {
        return false;
      }
    }
    return true;
  }
}
