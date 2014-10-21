package puzzles.nono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static puzzles.nono.ChunkVals.V.OFF;
import static puzzles.nono.ChunkVals.V.ON;
import static puzzles.nono.ChunkVals.V.UNCERTAIN;

public class ChunkVals {

  public enum V {
    ON("#"), OFF("_"), UNCERTAIN("*"),;
    private final String s;

    V(String s) {
      this.s = s;
    }

    @Override
    public String toString() {
      return s;
    }
  }

  public final List<V> values;

  public ChunkVals(int range) {
    values = new ArrayList<>(Collections.nCopies(range, OFF));
  }

  public void on(int i) {
    values.set(i, ON);
  }

  public void clear() {
    Collections.fill(values, OFF);
  }

  public puzzles.nono.ChunkVals copy() {
    ChunkVals copy = new ChunkVals(values.size());
    Collections.copy(copy.values, values);
    return copy;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (V i : values) {
      sb.append(i).append(",");
    }
    return sb.toString();
  }

  public void off(int i) {
    values.set(i, OFF);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ChunkVals)) {
      return false;
    }
    ChunkVals other = (ChunkVals) obj;
    for (int i = 0; i < values.size(); i++) {
      if (other.values.get(i) != values.get(i)) {
        return false;
      }
    }
    return true;
  }

  public int getChunkLength(int targetChunkNumber) {
    int i;
    int chunkNumber = 0;
    boolean inAChunk = false;
    for (i = 0; i < values.size(); i++) {
      if (values.get(i) == ON) {
        if (chunkNumber == targetChunkNumber) {
          break;
        }
        if (!inAChunk) {
          chunkNumber++;
          while (values.get(i + 1) == ON) {
            i++;
          }
        }
        inAChunk = true;
      } else {
        inAChunk = false;
      }
    }
    int j = i;
    for (; j < values.size(); j++) {
      if (values.get(j) == OFF) {
        break;
      }
    }
    int length = j - i;

    return length;
  }

  public void dunno(int i) {
    values.set(i, UNCERTAIN);
  }

  public int getNumChunks() {
    int i;
    int chunkNumber = 0;
    boolean inAChunk = false;
    for (i = 0; i < values.size(); i++) {
      if (values.get(i) == ON) {
        if (!inAChunk) {
          chunkNumber++;
        }
        inAChunk = true;
      } else {
        inAChunk = false;
      }
    }
    return chunkNumber;
  }
}