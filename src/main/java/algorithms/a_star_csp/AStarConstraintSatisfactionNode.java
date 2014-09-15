package algorithms.a_star_csp;

import java.util.ArrayList;
import java.util.List;

import algorithms.a_star.AStarNode;
import algorithms.csp.GeneralArchConsistency;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.09.2014.
 */
public class AStarConstraintSatisfactionNode extends AStarNode {

  private final GeneralArchConsistency gac;

  public AStarConstraintSatisfactionNode(GeneralArchConsistency gac) {
    this.gac = gac;
  }

  @Override
  protected int costFrom(AStarNode parent) {
    return 1;
  }

  @Override
  protected void generateSuccessors() {
    GeneralArchConsistency nextGac = gac.duplicate();
    Variable variable = nextGac.getSuccessor();
    Integer valueToAssume = variable.getDomain().iterator().next();
    variable.setAssumption(valueToAssume);
    AStarConstraintSatisfactionNode nextNode = new AStarConstraintSatisfactionNode(nextGac);
    List<AStarNode> succ = new ArrayList<>();
    succ.add(nextNode);
    setSuccsessors(succ);

    nextGac.run();
  }

  @Override
  protected void generateState() {
    setState(gac.getId());
  }

  @Override
  protected void generateHeuristic() {
    setHeuristic(gac.getDomainSize());
  }

  @Override
  public void visualize() {
    gac.visualize();
  }

  @Override
  public void devisualize() {
    gac.devisualize();
  }
}
