package algorithms.csp;

/**
 * Created by Patrick on 10.09.2014.
 */
public class ReviseRequest {

  private final Variable x;
  private final Constraint c;
  private boolean reduced;

  public ReviseRequest(Variable x, Constraint c) {
    this.x = x;
    this.c = c;
  }

  public Variable getFocalVariable() {
    return x;
  }

  public Constraint getConstraint() {
    return c;
  }

  public boolean isReduced() {
    return reduced;
  }

  public void setReduced(boolean reduced) {
    this.reduced = reduced;
  }
}
