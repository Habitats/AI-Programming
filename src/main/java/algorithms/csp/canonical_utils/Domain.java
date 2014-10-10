package algorithms.csp.canonical_utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Patrick on 04.09.2014.
 */
public class Domain<T> implements Iterable<T> {

  private static final String TAG = Domain.class.getSimpleName();
  private final Set<T> args;

  public Domain(T... args) {
    this.args = new HashSet(Arrays.asList(args));
    for (T arg : args) {
      this.args.add(arg);
    }
  }

  public Domain(Set<T> args) {
    this.args = new HashSet<>(args);
  }

  @Override
  public Iterator<T> iterator() {
    return args.iterator();
  }

  public boolean remove(T val) {
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
    for (T i : args) {
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
  public boolean equals(Object obj) {
    if (obj instanceof Domain) {
      Domain other = (Domain) obj;
      Set<T> otherArgs = other.args;
      return (args.containsAll(otherArgs)) && otherArgs.containsAll(args);
    }
    return false;
  }

  public String getId() {
    StringBuilder sb = new StringBuilder();
    for (T i : args) {
      sb.append(i).append(",");
    }
    return sb.toString();
  }
}
