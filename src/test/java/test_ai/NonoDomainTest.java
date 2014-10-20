package test_ai;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import puzzles.nono.NonoDomain;

/**
 * Created by Patrick on 16.10.2014.
 */
public class NonoDomainTest {
  @Test
  public void test_domainCreation(){
    List<Integer> specs = new ArrayList<>();
    specs.add(2);
    NonoDomain domain = new NonoDomain(specs, 5);

  }
}
