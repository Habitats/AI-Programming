package puzzles.shortestpath.gui;

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
import ai.gui.AIGridCanvas;
import ai.gui.AIGui;
import ai.gui.AISlider;
import ai.gui.AITextArea;
import ai.gui.AITextField;
import ai.models.grid.Board;
import algorithms.a_star.AStar;
import puzzles.shortestpath.Samples;
import puzzles.shortestpath.interfaces.ShortestPathButtonListener;

/**
 * Created by Patrick on 23.08.2014.
 */
public class ShortestPathGui extends AIGui {

  private static final String TAG = ShortestPathGui.class.getSimpleName();
  public static boolean DRAW_OUTLINES = true;
  public static boolean DRAW_CHILDREN = true;
  private AIGridCanvas drawingCanvas;
  private AIButton astarButton;
  private AIButton resetButton;
  private AIButton BFSButton;
  private AIButton DFSButton;
  private AIButton loadButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AITextArea inputField;
  private AITextField statusField;
  private AITextField logField;
  private AIComboBox comboBox1;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIButton simulationButton;
  private AIContiniousScrollPane log;
  private AICheckBox shuffleChildrenCheckBox;
  private AICheckBox drawChildrenCheckBox;
  private AICheckBox drawOutlinesCheckBox;
  private AICheckBox stepCheckBox;
  private AIButton fromFileButton;

  private ShortestPathButtonListener listener;

  public ShortestPathGui() {

    astarButton.addActionListener(e -> listener.astarClicked());
    resetButton.addActionListener(e -> listener.resetClicked());
    loadButton.addActionListener(e -> listener.loadClicked());
    BFSButton.addActionListener(e -> listener.bfsClicked());
    DFSButton.addActionListener(e -> listener.dfsClicked());
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
    simulationButton.addActionListener(e -> listener.simulationClicked());
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
      ShortestPathGui.DRAW_OUTLINES = checkbox.isSelected();

    });
    stepCheckBox.addActionListener(e -> {
      AICheckBox checkbox = (AICheckBox) e.getSource();
      AIMain.MANUAL_STEP = checkbox.isSelected();
    });

    buildFrame(mainPanel, log, statusField);
    fromFileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser("C:\\Dropbox\\code\\projects\\ai_prog\\samples");
      chooser.showOpenDialog(mainPanel);
      File file = chooser.getSelectedFile();
      inputField.setText(readFile(file.getPath(), Charset.defaultCharset()));
    });
  }

  public void setAdapter(Board adapter) {
    drawingCanvas.setAdapter(adapter);
    mainPanel.repaint();
  }

  @Override
  public String getInput() {
    return inputField.getText().trim();
  }

  public void setListener(ShortestPathButtonListener listener) {
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