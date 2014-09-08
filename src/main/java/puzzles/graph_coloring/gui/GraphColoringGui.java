package puzzles.graph_coloring.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.Log;
import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AICheckBox;
import ai.gui.AIComboBox;
import ai.gui.AIContiniousScrollPane;
import ai.gui.AIGui;
import ai.gui.AISlider;
import ai.gui.AITextArea;
import ai.gui.AITextField;
import ai.models.Board;
import algorithms.a_star.AStar;
import puzzles.graph_coloring.interfaces.GraphColoringButtonListener;
import puzzles.shortestpath.Samples;

/**
 * Created by Patrick on 23.08.2014.
 */
public class GraphColoringGui extends AIGui {

  private static final String TAG = GraphColoringGui.class.getSimpleName();
  public static boolean DRAW_OUTLINES = true;
  public static boolean DRAW_CHILDREN = true;
  private AIButton astarButton;
  private AIButton resetButton;
  private AIButton BFSButton;
  private AIButton DFSButton;
  private AIButton loadButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AICanvas drawingCanvas;
  private AITextArea inputField;
  private AITextField logField;
  private AITextField statusField;
  private AIComboBox comboBox1;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIButton simulationButton;
  private AIContiniousScrollPane log;
  private AICheckBox shuffleChildrenCheckBox;
  private AICheckBox drawChildrenCheckBox;
  private AICheckBox drawOutlinesCheckBox;
  private AICheckBox stepCheckBox;

  private GraphColoringButtonListener listener;

  public GraphColoringGui() {
    resetButton.addActionListener(e -> listener.resetClicked());
    loadButton.addActionListener(e -> listener.loadClicked());
    buildFrame(mainPanel, log, statusField);
    comboBox1.addActionListener(e -> {
      JComboBox cb = (JComboBox) e.getSource();
      int i = cb.getSelectedIndex();
      inputField.setText(Samples.getAstarSample(i));
    });
    stepButton.addActionListener(e -> listener.stepClicked());
    stepSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        Log.v(TAG, "slider event: " + e);
        AISlider slider = (AISlider) e.getSource();
        listener.stepChanged(slider.getValue());
      }
    });
    labelsCheckbox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      drawingCanvas.drawLabels(checkbox.isSelected());
    });
    shuffleChildrenCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      AStar.SHUFFLE_CHILDREN = checkbox.isSelected();
    });
    drawChildrenCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      DRAW_CHILDREN = checkbox.isSelected();
    });
    drawOutlinesCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      GraphColoringGui.DRAW_OUTLINES = checkbox.isSelected();

    });
    stepCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      AStar.MANUAL_STEP = checkbox.isSelected();
    });
  }

  public void setAdapter(Board adapter) {
    drawingCanvas.setAdapter(adapter);
    mainPanel.repaint();
  }

  public String getInput() {
    return inputField.getText().trim();
  }

  public void setListener(GraphColoringButtonListener listener) {
    this.listener = listener;
  }
}