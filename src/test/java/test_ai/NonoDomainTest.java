package test_ai;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import puzzles.nono.ChunkVals;
import puzzles.nono.NonoDomain;

import static org.junit.Assert.assertEquals;

/**
 * Created by Patrick on 16.10.2014.
 */
public class NonoDomainTest {

  @Test
  public void test_domainCreation() {
    List<Integer> specs = new ArrayList<>();
    specs.add(2);
    NonoDomain domain = new NonoDomain(specs, 5);

  }

  @Test
  public void test_chunkVals() {
    ChunkVals vals = new ChunkVals(6);
    vals.on(2);

    vals.on(4);
    vals.on(5);

    int numChunks = vals.getNumChunks();
    int chunkLength1 = vals.getChunkLength(0);
    int chunkLength2 = vals.getChunkLength(1);

    assertEquals(numChunks, 2);
    assertEquals(chunkLength1, 1);
    assertEquals(chunkLength2, 2);

    ChunkVals vals2 = new ChunkVals(6);
    vals2.on(2);
    vals2.on(3);

    vals2.on(5);

    int numChunks2 = vals2.getNumChunks();
    int chunkLength12 = vals2.getChunkLength(0);
    int chunkLength22 = vals2.getChunkLength(1);

    assertEquals(numChunks2, 2);
    assertEquals(chunkLength12, 2);
    assertEquals(chunkLength22, 1);

  }
}
