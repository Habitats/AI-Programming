package ai;

import java.awt.*;

import javax.swing.*;

import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AIGui;
import ai.gui.AIPanel;
import ai.gui.AITextArea;
import puzzles.flow.Flow;
import puzzles.game20480.Game2048;
import puzzles.graph_coloring.GraphColoring;
import puzzles.nono.Nono;
import puzzles.shortestpath.ShortestPath;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AIMain {

  public static final String TAG = AIMain.class.getSimpleName();

  public static void main(String[] args) {
    loadGui();
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
        AIButton g2048Button = new AIButton("2048");
        shortestPathButton.addActionListener(e -> shortestPath());
        graphColoringButton.addActionListener(e -> graphColoring());
        flowButton.addActionListener(e -> flow());
        nonoButton.addActionListener(e -> nono());
        g2048Button.addActionListener(e -> g2048());
        panel.add(shortestPathButton);
        panel.add(graphColoringButton);
        panel.add(flowButton);
        panel.add(nonoButton);
        panel.add(g2048Button);
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

  private static void g2048() {
    new Thread(new Game2048()).start();
  }

  private static void shortestPath() {
    new Thread(new ShortestPath()).start();
  }

  private static void graphColoring() {
    new Thread(new GraphColoring()).start();
  }
}
