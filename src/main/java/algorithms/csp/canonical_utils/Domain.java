package algorithms.csp.canonical_utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Domain implements Iterable<Integer> {

  private static final String TAG = Domain.class.getSimpleName();
  private Set<Integer> args;

  public Domain(Integer... args) {
    this.args = new HashSet(Arrays.asList(args));
  }

  public Domain(Set<Integer> args) {
    this.args = new HashSet<>(args);
  }

  public Domain(int[] args) {
    this.args = new HashSet<>();
    for (int i = 0; i < args.length; i++) {
      this.args.add(new Integer(args[i]));
    }
  }

  @Override
  public Iterator<Integer> iterator() {
    return args.iterator();
  }

  public boolean remove(Integer val) {
    return args.remove(val);
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
    return "D: " + domain.substring(2);
  }

  public boolean iEmpty() {
    return args.isEmpty();
  }


  public Domain copy() {
    return new Domain(args);
  }

  @Override
  public boolean equals(Object other) {
    Set<Integer> otherArgs = ((Domain) other).args;
    return (args.containsAll(otherArgs)) && otherArgs.containsAll(args);
  }

  public String getId() {
    StringBuilder sb = new StringBuilder();
    for (Integer i : args) {
      sb.append(i + ",");
    }
    return sb.toString();
  }
}
