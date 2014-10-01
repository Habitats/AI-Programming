package ai;

import java.awt.*;

import javax.swing.*;

import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AIGui;
import ai.gui.AIPanel;
import ai.gui.AITextArea;
import puzzles.graph_coloring.GraphColoring;
import puzzles.shortestpath.ShortestPath;

/**
 * Created by Patrick on 24.08.2014.
 */
public class AIMain {

  public static final String TAG = AIMain.class.getSimpleName();

  public static void main(String[] args) {
//    graphColoring();
//    shortestPath();
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
        shortestPathButton.addActionListener(e -> shortestPath());
        graphColoringButton.addActionListener(e -> graphColoring());
        panel.add(shortestPathButton);
        panel.add(graphColoringButton);
        super.buildFrame(panel, null, null);
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

  public static void shortestPath() {
    new Thread(new ShortestPath()).start();
  }

  public static void graphColoring() {
    new Thread(new GraphColoring()).start();
  }
}
