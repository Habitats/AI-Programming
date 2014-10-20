package puzzles.nono;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import algorithms.csp.canonical_utils.Domain;


/**
 * Created by Patrick on 14.10.2014.
 */
public class NonoDomain extends Domain<ChunkVals> {

  private final int range;

  public NonoDomain(List<Integer> specs, int range) {
    super();
    this.range = range;
    int index = 0;
    Queue<Chunk> chunkQueue = new PriorityQueue<>();
    for (Integer length : specs) {
      Chunk chunk = new Chunk(length, index++);
      chunkQueue.add(chunk);
    }

    ChunkVals chunkVals = new ChunkVals(range);
    SubDomain domain = new SubDomain(range);
    placeSubChunk(chunkQueue, domain, chunkVals, 0, range);
  }

  private void placeSubChunk(Queue<Chunk> chunkQueue, SubDomain domain, ChunkVals chunkVals, int firstStartPosition,
                             int range) {
    Chunk chunk = chunkQueue.poll();
    int lastStartPosition = range - getMinimumRequiredRange(chunkQueue, chunk);
    for (int startPosition = firstStartPosition; startPosition <= lastStartPosition; startPosition++) {
      placeChunk(chunk.length, startPosition, chunkVals);
      if (chunkQueue.isEmpty()) {
        addChunk(chunkVals);
//        chunkVals.clear();
      } else {
        placeSubChunk(copy(chunkQueue), new SubDomain(domain.length - (startPosition + chunk.length + 1)),
                      chunkVals.copy(), startPosition + chunk.length + 1, range);
      }
      clearChunks(chunk.length, startPosition, chunkVals);
    }
  }

  private void clearChunks(int length, int start, ChunkVals chunkVals) {
    for (int i = start; i < start + length; i++) {
      chunkVals.off(i);
    }
  }

  private int getMinimumRequiredRange(Queue<Chunk> chunkQueue, Chunk chunk) {
    int min = chunk.length + 1;
    for (Chunk o : chunkQueue) {
      min += o.length + 1;
    }
    min--;
    return min;
  }

  private Queue<Chunk> copy(Queue<Chunk> chunkQueue) {
    Queue<Chunk> copy = new PriorityQueue<>();
    for (Chunk chunk : chunkQueue) {
      copy.add(new Chunk(chunk.length, chunk.index));
    }
    return copy;
  }

  private void addChunk(ChunkVals chunkVals) {
    getArgs().add(chunkVals.copy());
  }

  public ChunkVals getCertainValues() {
    ChunkVals certainFilledVals = new ChunkVals(range);
    for (int i = 0; i < range; i++) {
      certainFilledVals.on(i);
    }

    for (ChunkVals chunk : getArgs()) {
      for (int i = 0; i < chunk.values.size(); i++) {
        if (chunk.values.get(i) == 0) {
          certainFilledVals.off(i);
        }
      }
    }
//
//    ChunkVals certainEmptyVals = new ChunkVals(range);
//    for (int i = 0; i < range; i++) {
//      certainEmptyVals.on(i);
//    }
//
//    for (ChunkVals chunk : getArgs()) {
//      for (int i = 0; i < chunk.values.size(); i++) {
//        if (chunk.values.get(i) == 1) {
//          certainEmptyVals.off(i);
//        }
//      }
//    }
//
//    ChunkVals certainVals = new ChunkVals(range);
//    for (int i = 0; i < range; i++) {
//      if (certainEmptyVals.values.get(i) == 1) {
//        certainVals.off(i);
//      } else if (certainFilledVals.values.get(i) == 1) {
//        certainVals.on(i);
//      } else {
//        certainVals.dunno(i);
//      }
//    }
    return certainFilledVals;
  }


  private void placeChunk(int length, int start, ChunkVals chunkVals) {
    for (int i = start; i < start + length; i++) {
      chunkVals.on(i);
    }
  }

  private int getMinimumRequiredRange(List<Integer> specs) {
    int minimumRequiredRange = specs.size() - 1;
    for (Integer chunk : specs) {
      minimumRequiredRange += chunk;
    }
    return minimumRequiredRange;
  }

  private class Chunk implements Comparable<Chunk> {

    public final int length;
    private final int index;

    public Chunk(Integer length, int index) {
      this.length = length;
      this.index = index;
    }

    @Override
    public String toString() {
      return String.valueOf(length);
    }

    @Override
    public int compareTo(Chunk o) {
      return index - o.index;
    }
  }

  private class SubDomain {

    public final int length;

    public SubDomain(int length) {
      this.length = length;
    }
  }

}
