package puzzles.graph_coloring.gui;

import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.AIMain;
import ai.Log;
import ai.gui.AIButton;
import ai.gui.AICanvas;
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
import algorithms.csp.CspButtonListener;
import puzzles.graph_coloring.GraphColoringUtils;

/**
 * Created by Patrick on 23.08.2014.
 */
public class GraphColoringGui extends AIGui<ColorNode> {

  private static final String TAG = GraphColoringGui.class.getSimpleName();
  private AIButton resetButton;
  private AIButton runButton;
  private AIButton readFromFileButton;
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
      AIMain.MANUAL_STEP = checkbox.isSelected();
    });
    resetButton.addActionListener(e -> listener.resetClicked());
    runButton.addActionListener(e -> listener.runClicked());
    readFromFileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser("C:\\Dropbox\\code\\projects\\ai_prog\\samples");
      chooser.showOpenDialog(mainPanel);
      File file = chooser.getSelectedFile();
      inputField.setText(readFile(file.getPath(), Charset.defaultCharset()));
    });
    loadButton.addActionListener(e -> listener.loadClicked());
  }


  @Override
  public void setAdapter(AIAdapter<ColorNode> adapter) {

//    setAdapter.setOrigin(minX, minY);

    drawingCanvas.setAdapter(adapter);
    Log.v(TAG, "setting setAdapter!" + adapter);
    mainPanel.repaint();
  }

  @Override
  public String getInput() {
    return inputField.getText().trim();
  }

  public String getK() {
    return kField.getText().trim();
  }

  @Override
  public void setListener(CspButtonListener listener) {
    this.listener = listener;
  }

  @Override
  protected int getDefaultCloseOperation() {
    return WindowConstants.DISPOSE_ON_CLOSE;
  }

  @Override
  protected Dimension getPreferredSize() {
    return new Dimension(900, 700);
  }

  @Override
  public JPanel getMainPanel() {
    return mainPanel;
  }

  @Override
  public AICanvas getDrawingCanvas() {
    return drawingCanvas;
  }

  @Override
  public AITextArea getInputField() {
    return inputField;
  }

}