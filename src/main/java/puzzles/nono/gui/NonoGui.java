package puzzles.nono.gui;

import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.Log;
import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AICheckBox;
import ai.gui.AIComboBox;
import ai.gui.AIContiniousScrollPane;
import ai.gui.AIGridCanvas;
import ai.gui.AIGui;
import ai.gui.AISlider;
import ai.gui.AITextArea;
import ai.gui.AITextField;
import algorithms.a_star.AStar;
import puzzles.nono.NonoUtils;

/**
 * Created by Patrick on 23.08.2014.
 */
public class NonoGui extends AIGui {

  private static final String TAG = NonoGui.class.getSimpleName();
  private AIButton resetButton;
  private AIButton runButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AIGridCanvas drawingCanvas;
  private AITextArea inputField;
  private AITextField logField;
  private AITextField statusField;
  private AITextField kField;
  private AIComboBox sampleComboBox;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIContiniousScrollPane log;
  private AICheckBox stepCheckBox;
  private AIButton readFromFileButton;

  public NonoGui() {
    buildFrame(getMainPanel(), log, statusField);
    sampleComboBox.addActionListener(e -> {
      JComboBox cb = (JComboBox) e.getSource();
      int i = cb.getSelectedIndex();
      listener.sampleSelected(i);
      try {
        getInputField().setText(NonoUtils.samples.get(i));
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
      getDrawingCanvas().drawLabels(checkbox.isSelected());
    });
    stepCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      AStar.MANUAL_STEP = checkbox.isSelected();
    });
    resetButton.addActionListener(e -> listener.resetClicked());
    runButton.addActionListener(e -> listener.runClicked());
    readFromFileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser("C:\\Dropbox\\code\\projects\\ai_prog\\samples");
      chooser.showOpenDialog(getMainPanel());
      File file = chooser.getSelectedFile();
      getInputField().setText(readFile(file.getPath(), Charset.defaultCharset()));
    });
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