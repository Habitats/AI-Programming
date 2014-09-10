package puzzles.graph_coloring.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.Log;
import ai.gui.AIButton;
import ai.gui.AICheckBox;
import ai.gui.AIComboBox;
import ai.gui.AIContiniousScrollPane;
import ai.gui.AIGraphCanvas;
import ai.gui.AIGui;
import ai.gui.AISlider;
import ai.gui.AITextArea;
import ai.gui.AITextField;
import ai.models.AIAdapter;
import ai.models.ColorNode;
import algorithms.a_star.AStar;
import puzzles.graph_coloring.GraphInputUtils;
import puzzles.graph_coloring.interfaces.GraphColoringButtonListener;

/**
 * Created by Patrick on 23.08.2014.
 */
public class GraphColoringGui extends AIGui {

  private static final String TAG = GraphColoringGui.class.getSimpleName();
  public static boolean DRAW_OUTLINES = true;
  public static boolean DRAW_CHILDREN = true;
  private AIButton resetButton;
  private AIButton loadButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AIGraphCanvas drawingCanvas;
  private AITextArea inputField;
  private AITextField logField;
  private AITextField statusField;
  private AIComboBox sampleComboBox;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIButton simulationButton;
  private AIContiniousScrollPane log;
  private AICheckBox drawOutlinesCheckBox;
  private AICheckBox stepCheckBox;

  private GraphColoringButtonListener listener;

  public GraphColoringGui() {
    resetButton.addActionListener(e -> listener.resetClicked());
    loadButton.addActionListener(e -> listener.loadClicked());
    buildFrame(mainPanel, log, statusField);
    sampleComboBox.addActionListener(e -> {
      JComboBox cb = (JComboBox) e.getSource();
      int i = cb.getSelectedIndex();
      listener.sampleSelected(i);
      try {
        inputField.setText(GraphInputUtils.samples.get(i));
      } catch (IndexOutOfBoundsException ex) {
        Log.v(TAG, "no such sample!");
      }

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
    drawOutlinesCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      GraphColoringGui.DRAW_OUTLINES = checkbox.isSelected();

    });
    stepCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      AStar.MANUAL_STEP = checkbox.isSelected();
    });
  }

  public void setAdapter(AIAdapter<ColorNode> adapter) {

//    adapter.setOrigin(minX, minY);

    drawingCanvas.setAdapter(adapter);
    Log.v(TAG, "setting adapter!" + adapter);
    mainPanel.repaint();
  }

  public String getInput() {
    return inputField.getText().trim();
  }

  public void setListener(GraphColoringButtonListener listener) {
    this.listener = listener;
  }
}