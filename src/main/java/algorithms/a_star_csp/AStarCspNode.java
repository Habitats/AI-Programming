package algorithms.a_star_csp;

import java.util.ArrayList;
import java.util.List;

import algorithms.a_star.AStarNode;
import algorithms.csp.GeneralArchConsistency;
import algorithms.csp.canonical_utils.Variable;

/**
 * Created by Patrick on 15.09.2014.
 */
public class AStarCspNode extends AStarNode {

  private static final String TAG = AStarCspNode.class.getSimpleName();
  private final AStarCspPuzzle puzzle;
  private GeneralArchConsistency.Result res;

  public AStarCspNode(AStarCspPuzzle puzzle) {
    super();
    this.puzzle = puzzle;
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

    List<AStarNode> succ = new ArrayList<>();
    Variable successorVariable = puzzle.getSuccessor();
    for(Integer value : successorVariable.getDomain()){

      AStarCspPuzzle next = puzzle.duplicate();
      Variable variable = next.getSuccessor();
      variable.setAssumption(value);

      GeneralArchConsistency.Result domainFilteringResult = GeneralArchConsistency.domainFilter(next);
      AStarCspNode nextNode = new AStarCspNode(next);
      nextNode.setState(domainFilteringResult);
      succ.add(nextNode);
    }
    setSuccsessors(succ);

  }

  @Override
  protected void generateState() {
    setState(puzzle.getId());
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
      int domainDelta = puzzle.getDomainSize() - puzzle.getVariables().size();
      setHeuristic(domainDelta);
    }
  }

  @Override
  public void visualize() {
    puzzle.visualize();
  }

  @Override
  public void devisualize() {
  }
}
