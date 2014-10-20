package puzzles.nono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkVals {


  public final List<Integer> values;

  public ChunkVals(int range) {
    values = new ArrayList<>(Collections.nCopies(range, 0));
  }

  public void on(int i) {
    values.set(i, 1);
  }

  public void clear() {
    Collections.fill(values, 0);
  }

  public puzzles.nono.ChunkVals copy() {
    ChunkVals copy = new ChunkVals(values.size());
    Collections.copy(copy.values, values);
    return copy;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Integer i : values) {
      sb.append(i).append(",");
    }
    return sb.toString();
  }

  public void off(int i) {
    values.set(i, 0);
  }

  public void dunno(int i) {
    values.set(i, 3);
  }
}