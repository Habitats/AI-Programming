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
  private  GeneralArchConsistency.Result res;

  public AStarConstraintSatisfactionNode(GeneralArchConsistency gac) {
    super();
    this.gac = gac;
  }

  public void setState(GeneralArchConsistency.Result res) {
    this.res = res;
  }

  @Override
  protected int costFrom(AStarNode parent) {
    return 1;
  }

  @Override
  protected void generateSuccessors() {
    GeneralArchConsistency nextGac = gac.duplicate();

    doAssumption(nextGac);
    GeneralArchConsistency.Result domainFilteringResult = nextGac.domainFilter();
    AStarConstraintSatisfactionNode nextNode = new AStarConstraintSatisfactionNode(nextGac);
    nextNode.setState(domainFilteringResult);
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
    if (res == GeneralArchConsistency.Result.UNCHANGED_DOMAIN) {
      setHeuristic(Integer.MAX_VALUE);
    } else if (res == GeneralArchConsistency.Result.SOLUTION) {
      setHeuristic(0);
    } else if (res == GeneralArchConsistency.Result.UNCHANGED_DOMAIN) {
      setHeuristic(Integer.MAX_VALUE);
    } else if (res == GeneralArchConsistency.Result.EMPTY_DOMAIN) {
      setHeuristic(Integer.MAX_VALUE);
    } else {
      int domainDelta = gac.getDomainSize() - gac.getNumVariables();
      setHeuristic(domainDelta);
    }
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
