package ai;

import java.awt.*;

import javax.swing.*;

import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AIGui;
import ai.gui.AIPanel;
import ai.gui.AITextArea;
import algorithms.csp.CspPuzzle;
import algorithms.csp.GeneralArchConsistency;
import puzzles.flow.Flow;
import puzzles.graph_coloring.GraphColoring;
import puzzles.nono.Nono;
import puzzles.shortestpath.ShortestPath;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AIMain {

  public static final String TAG = AIMain.class.getSimpleName();

  public static void main(String[] args) {
//    graphColoring();
//    shortestPath();
    loadGui();
//    flowTest();
  }

  private static void loadGui() {
    new AIGui() {

      @Override
      protected int getDefaultCloseOperation() {
        return WindowConstants.EXIT_ON_CLOSE;
      }

      @Override
      protected Dimension getPreferredSize() {
        return new Dimension(300, 200);
      }

      @Override
      protected void init() {
        AIPanel panel = new AIPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        AIButton shortestPathButton = new AIButton("Shortest Path");
        AIButton graphColoringButton = new AIButton("Graph Coloring");
        AIButton flowButton = new AIButton("Flow Free");
        AIButton nonoButton = new AIButton("Nonogram");
        shortestPathButton.addActionListener(e -> shortestPath());
        graphColoringButton.addActionListener(e -> graphColoring());
        flowButton.addActionListener(e -> flow());
        nonoButton.addActionListener(e -> nono());
        panel.add(shortestPathButton);
        panel.add(graphColoringButton);
        panel.add(flowButton);
        panel.add(nonoButton);
        buildFrame(panel, null, null);
      }

      @Override
      public JPanel getMainPanel() {
        return null;
      }

      @Override
      public AICanvas getDrawingCanvas() {
        return null;
      }

      @Override
      public AITextArea getInputField() {
        return null;
      }
    }.init();
  }

  private static void nono() {
    new Thread(new Nono()).start();
  }

  private static void flow() {
    new Thread(new Flow()).start();
  }

  private static void flowTest() {
    Flow flow = new Flow();
    flow.run();
    CspPuzzle puzzle = flow.getSamplePuzzle(5);
    int domainSize = puzzle.getDomainSize();
    GeneralArchConsistency.printVariables(puzzle);
    GeneralArchConsistency.Result res = GeneralArchConsistency.domainFilter(puzzle);
    GeneralArchConsistency.printVariables(puzzle);
    puzzle.setAssumption("id_x1y0", 0);
    puzzle.visualize();
    res = GeneralArchConsistency.domainFilter(puzzle);
    GeneralArchConsistency.printVariables(puzzle);
    puzzle.setAssumption("id_x0y1", 0);
    puzzle.visualize();
    res = GeneralArchConsistency.domainFilter(puzzle);
    GeneralArchConsistency.printVariables(puzzle);

    puzzle.visualize();
  }

  private static void shortestPath() {
    new Thread(new ShortestPath()).start();
  }

  private static void graphColoring() {
    new Thread(new GraphColoring()).start();
  }
}
