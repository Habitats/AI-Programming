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
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import puzzles.graph_coloring.GraphColoringUtils;
import puzzles.graph_coloring.gui.interfaces.CspButtonListener;

/**
 * Created by Patrick on 23.08.2014.
 */
public class GraphColoringGui extends AIGui {

  private static final String TAG = GraphColoringGui.class.getSimpleName();
  private AIButton resetButton;
  private AIButton loadButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AIGraphCanvas drawingCanvas;
  private AITextArea inputField;
  private AITextField logField;
  private AITextField statusField;
  private AITextField kField;
  private AIComboBox sampleComboBox;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIContiniousScrollPane log;
  private AICheckBox stepCheckBox;

  private CspButtonListener listener;

  public GraphColoringGui() {
    buildFrame(mainPanel, log, statusField);
    sampleComboBox.addActionListener(e -> {
      JComboBox cb = (JComboBox) e.getSource();
      int i = cb.getSelectedIndex();
      listener.sampleSelected(i);
      try {
        inputField.setText(GraphColoringUtils.samples.get(i));
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
    stepCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      AStar.MANUAL_STEP = checkbox.isSelected();
    });
    resetButton.addActionListener(e -> listener.resetClicked());
    loadButton.addActionListener(e -> {
      listener.loadClicked();
    });
  }

  public void setAdapter(AIAdapter<ColorNode> adapter) {

//    setAdapter.setOrigin(minX, minY);

    drawingCanvas.setAdapter(adapter);
    Log.v(TAG, "setting setAdapter!" + adapter);
    mainPanel.repaint();
  }

  public String getInput() {
    return inputField.getText().trim();
  }
  public String getK(){
    return kField.getText().trim();
  }

  public void setListener(CspButtonListener listener) {
    this.listener = listener;
  }

}