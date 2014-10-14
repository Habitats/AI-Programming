package puzzles.nono;

import java.util.Arrays;
import java.util.List;

import algorithms.csp.canonical_utils.Domain;

/**
 * Created by Patrick on 14.10.2014.
 */
public class NonoDomain extends Domain<NonoLineValues> {

  public NonoDomain(List<Integer> specs, int range) {
    super();
    int minRange = getMinimumRequiredRange(specs);
    // if the chunk is as big as the range, only one chucnk is required in the domain
    if (range == minRange) {
      int[] chunkVals = new int[range];
      int index = 0;
      for (Integer i : specs) {
        for (int v = 0; v < i; v++) {
          chunkVals[index++] = 1;
        }
        // add the space between the chunks
        if (index < chunkVals.length) {
          chunkVals[index++] = 0;
        }
      }
      getArgs().add(new NonoLineValues(chunkVals));
    } else {
      addChunkPermutations(specs, range);
    }
  }

  private void addChunkPermutations(List<Integer> specs, int range) {
    // the minimum required range for the entire line
    int minRange = getMinimumRequiredRange(specs);

    // the indexes the first chunk can start
    int delta = range - minRange;

    // the actual values of the line (0 and 1)

    // for each start index
    for (int start = 0; start < delta; start++) {
      int[] chunkVals = new int[range];

      // which chunk to place
      int next = 0;

      // the length of the chunk to place
      int length = specs.get(next);

      // place the chunk in the chunkvals
      addChunk(start, length, chunkVals);

      // place the remaining chunks
      int startPositionOfNext = start + length + 1;

      addCombinations(startPositionOfNext, ++next, specs, chunkVals, range, delta - start);
    }
  }

  private void addCombinations(int startPositionOfNext, int indexOfNext, List<Integer> specs, int[] chunkVals,
                               int range, int spaceLeft) {
    int lengthNext;
    // if this is the last chunk in spec, do something lol
    if (indexOfNext < specs.size()) {
      lengthNext = specs.get(indexOfNext);
    } else {
      getArgs().add(new NonoLineValues(chunkVals));
      return;
    }
    if (spaceLeft == 0) {
      return;
    }
    if (startPositionOfNext + lengthNext - 1 == range) {
      addChunk(startPositionOfNext, lengthNext, chunkVals);
      getArgs().add(new NonoLineValues(chunkVals));
    } else {
      for (int start = startPositionOfNext; start < startPositionOfNext + spaceLeft; start++) {
        if (start + lengthNext < chunkVals.length) {
          addChunk(start, lengthNext, chunkVals);
          startPositionOfNext = start + lengthNext + 1;
          int[] newChunkVals = Arrays.copyOf(chunkVals, chunkVals.length);
          addCombinations(startPositionOfNext, ++indexOfNext, specs, newChunkVals, range, spaceLeft - start);
        }
      }
    }
  }

  private void addChunk(int start, int length, int[] chunkVals) {
    for (int i = start; i < start + length; i++) {
      chunkVals[i] = 1;
    }
  }

  private int getMinimumRequiredRange(List<Integer> specs) {
    int minimumRequiredRange = specs.size() - 1;
    for (Integer chunk : specs) {
      minimumRequiredRange += chunk;
    }
    return minimumRequiredRange;
  }


}
