package puzzles.flow.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

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
import ai.gui.AIPanel;
import ai.gui.AISlider;
import ai.gui.AISplitPane;
import ai.gui.AITextArea;
import ai.gui.AITextField;
import ai.models.AIAdapter;
import ai.models.grid.ColorTile;
import algorithms.a_star.AStar;
import algorithms.csp.CspButtonListener;
import puzzles.flow.FlowUtils;

/**
 * Created by Patrick on 23.08.2014.
 */
public class FlowGui extends AIGui<ColorTile> {

  private static final String TAG = FlowGui.class.getSimpleName();
  private AIButton resetButton;
  private AIButton runButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AIGridCanvas drawingCanvas;
  private AITextArea inputField;
  private AITextField logField;
  private AITextField statusField;
  private AIComboBox sampleComboBox;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIContiniousScrollPane log;
  private AICheckBox stepCheckBox;
  private AIButton readFromFileButton;
  private AIButton loadButton;

  private CspButtonListener listener;

  public FlowGui() {
    buildFrame(mainPanel, log, statusField);
    sampleComboBox.addActionListener(e -> {
      JComboBox cb = (JComboBox) e.getSource();
      int i = cb.getSelectedIndex();
      listener.sampleSelected(i);
      try {
        inputField.setText(FlowUtils.samples.get(i));
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
    runButton.addActionListener(e -> {
      listener.runClicked();
    });
    readFromFileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser("C:\\Dropbox\\code\\projects\\ai_prog\\samples");
      chooser.showOpenDialog(mainPanel);
      File file = chooser.getSelectedFile();
      inputField.setText(readFile(file.getPath(), Charset.defaultCharset()));
    });
    loadButton.addActionListener(e -> {
      listener.loadClicked();
    });
  }


  @Override
  public void setAdapter(AIAdapter<ColorTile> adapter) {

//    setAdapter.setOrigin(minX, minY);

    drawingCanvas.setAdapter(adapter);
    Log.v(TAG, "setting setAdapter!" + adapter);
    mainPanel.repaint();
  }

  @Override
  public String getInput() {
    return inputField.getText().trim();
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

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.setBackground(new Color(-3496020));
    mainPanel.putClientProperty("html.disable", Boolean.FALSE);
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                              GridConstraints.SIZEPOLICY_CAN_SHRINK
                                              | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED,
                                              null, null, null, 0, false));
    logField = new AITextField();
    panel1.add(logField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                                             GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                               | GridConstraints.SIZEPOLICY_CAN_GROW,
                                             null, null, null, 0, false));
    statusField = new AITextField();
    statusField.setHorizontalAlignment(4);
    statusField.setText("status");
    panel1.add(statusField,
               new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                   GridConstraints.SIZEPOLICY_FIXED,
                                   GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                                   null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(12, 2, new Insets(0, 0, 0, 0), 0, -1));
    mainPanel.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                              GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                                              new Dimension(50, -1), new Dimension(140, -1), null, 0, false));
    stepButton = new AIButton();
    stepButton.setText("Step");
    panel2.add(stepButton,
               new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                   GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                   GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    sampleComboBox = new AIComboBox();
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    defaultComboBoxModel1.addElement("flowspec-0");
    defaultComboBoxModel1.addElement("flowspec-1");
    defaultComboBoxModel1.addElement("flowspec-2");
    defaultComboBoxModel1.addElement("flowspec-3");
    defaultComboBoxModel1.addElement("flowspec-4");
    defaultComboBoxModel1.addElement("flowspec-5");
    defaultComboBoxModel1.addElement("flowspec-6");
    sampleComboBox.setModel(defaultComboBoxModel1);
    panel2.add(sampleComboBox,
               new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                                   GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null,
                                   null, 0, false));
    runButton = new AIButton();
    runButton.setText("Run");
    panel2.add(runButton,
               new GridConstraints(10, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                   GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                   GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    resetButton = new AIButton();
    resetButton.setText("Stop");
    panel2.add(resetButton,
               new GridConstraints(11, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                   GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                   GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final AIPanel aIPanel1 = new AIPanel();
    panel2.add(aIPanel1,
               new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null,
                                   new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
    stepSlider = new AISlider();
    stepSlider.setMajorTickSpacing(100);
    stepSlider.setMaximum(500);
    stepSlider.setMinimum(1);
    stepSlider.setMinorTickSpacing(50);
    stepSlider.setPaintLabels(false);
    stepSlider.setPaintTrack(true);
    stepSlider.setSnapToTicks(true);
    stepSlider.setValueIsAdjusting(true);
    panel2.add(stepSlider,
               new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, 1,
                                   GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    labelsCheckbox = new AICheckBox();
    labelsCheckbox.setSelected(false);
    labelsCheckbox.setText("Labels");
    panel2.add(labelsCheckbox,
               new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1,
                                   GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    stepCheckBox = new AICheckBox();
    stepCheckBox.setText("Should step");
    panel2.add(stepCheckBox, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                                 GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                 | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                 GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                 | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JScrollPane scrollPane1 = new JScrollPane();
    panel2.add(scrollPane1, new GridConstraints(1, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                                                GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    inputField = new AITextArea();
    inputField.setWrapStyleWord(false);
    scrollPane1.setViewportView(inputField);
    readFromFileButton = new AIButton();
    readFromFileButton.setText("From File");
    panel2.add(readFromFileButton,
               new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                   GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null,
                                   null, 0, false));
    loadButton = new AIButton();
    loadButton.setText("Load");
    panel2.add(loadButton, new GridConstraints(9, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                               GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED,
                                               null, null, null, 0, false));
    final AISplitPane aISplitPane1 = new AISplitPane();
    aISplitPane1.setBackground(new Color(-12516858));
    aISplitPane1.setContinuousLayout(true);
    aISplitPane1.setOneTouchExpandable(false);
    aISplitPane1.setOrientation(0);
    aISplitPane1.setResizeWeight(0.8);
    mainPanel.add(aISplitPane1,
                  new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
                                      new Dimension(500, 600), null, 0, false));
    log = new AIContiniousScrollPane();
    aISplitPane1.setRightComponent(log);
    drawingCanvas = new AIGridCanvas();
    drawingCanvas.setLayout(new GridBagLayout());
    aISplitPane1.setLeftComponent(drawingCanvas);
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainPanel;
  }
}