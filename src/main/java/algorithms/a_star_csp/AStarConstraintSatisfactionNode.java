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

  private static final String TAG = AStarConstraintSatisfactionNode.class.getSimpleName();
  private final GeneralArchConsistency gac;

  public AStarConstraintSatisfactionNode(GeneralArchConsistency gac) {
    super();
    this.gac = gac;
  }

  @Override
  protected int costFrom(AStarNode parent) {
    return 1;
  }

  @Override
  protected void generateSuccessors() {
    GeneralArchConsistency nextGac = gac.duplicate();

    doAssumption(nextGac);
    nextGac.domainFilter();
    AStarConstraintSatisfactionNode nextNode = new AStarConstraintSatisfactionNode(nextGac);
    List<AStarNode> succ = new ArrayList<>();
    succ.add(nextNode);
    setSuccsessors(succ);

  }

  private void doAssumption(GeneralArchConsistency nextGac) {
    Variable variable = nextGac.getSuccessor();
    Integer valueToAssume = variable.getDomain().iterator().next();
    variable.setAssumption(valueToAssume);
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
