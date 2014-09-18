import org.junit.Test;

import algorithms.csp.canonical_utils.Domain;
import algorithms.csp.canonical_utils.Variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by Patrick on 18.09.2014.
 */
public class GacTests {


  @Test
  public void test_variable_copy() {
    System.out.println("Test variable COPY...");
    Variable var = new Variable("n0", new Domain(1, 2, 3, 4));
    Variable var2 = var.copy();
    assertEquals(var, var2);

    var.setValue(1);

    assertNotEquals(var, var2);

    var2.setValue(1);
    assertEquals(var, var2);

    var2.setAssumption(2);
    assertNotEquals(var, var2);

    var.setAssumption(2);
    assertEquals(var, var2);
  }

  @Test
  public void test_domain_copy() {
    System.out.println("Test domain COPY...");
    Domain dom = new Domain(1, 2, 3, 4, 5);
    Domain dom2 = dom.copy();

    assertEquals(dom, dom2);
    dom.remove(1);

    assertNotEquals(dom, dom2);

    dom2.remove(1);
    assertEquals(dom, dom2);

    Domain dom3 = new Domain(1, 2, 3, 4);
    dom.remove(5);
    dom3.remove(1);
    assertEquals(dom3, dom);
  }
}
