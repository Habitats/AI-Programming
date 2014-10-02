package puzzles.graph_coloring.gui;

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
import ai.gui.AIGraphCanvas;
import ai.gui.AIGui;
import ai.gui.AILabel;
import ai.gui.AIPanel;
import ai.gui.AISlider;
import ai.gui.AISplitPane;
import ai.gui.AITextArea;
import ai.gui.AITextField;
import ai.models.AIAdapter;
import ai.models.graph.ColorNode;
import algorithms.a_star.AStar;
import algorithms.csp.CspButtonListener;
import puzzles.graph_coloring.GraphColoringUtils;

/**
 * Created by Patrick on 23.08.2014.
 */
public class GraphColoringGui extends AIGui<ColorNode> {

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
  private AIButton readFromFileButton;

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
    readFromFileButton.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser("C:\\Dropbox\\code\\projects\\ai_prog\\samples");
      chooser.showOpenDialog(mainPanel);
      File file = chooser.getSelectedFile();
      inputField.setText(readFile(file.getPath(), Charset.defaultCharset()));
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

  public String getK() {
    return kField.getText().trim();
  }

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

  private void createUIComponents() {
    // TODO: place custom component creation code here
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
    mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.setBackground(new Color(-3496020));
    mainPanel.putClientProperty("html.disable", Boolean.FALSE);
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2,
                                                                           com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                           com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                                           com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                           | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                           com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                           null, null, null, 0, false));
    logField = new AITextField();
    panel1.add(logField, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                                                                          com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                                                                          com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                          com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                          com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                          | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                          null, null, null, 0, false));
    statusField = new AITextField();
    statusField.setHorizontalAlignment(4);
    statusField.setText("status");
    panel1.add(statusField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                                                                             com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                             com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                             com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                             com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                             | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                             null, null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(12, 2, new Insets(0, 0, 0, 0), 0, -1));
    mainPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                                                                           com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                           com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                                           com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                           com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                           new Dimension(50, -1),
                                                                           new Dimension(140, -1), null, 0, false));
    stepButton = new AIButton();
    stepButton.setText("Step");
    panel2.add(stepButton, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 2,
                                                                            com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                            com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                            com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                            com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                            null, null, null, 0, false));
    sampleComboBox = new AIComboBox();
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    defaultComboBoxModel1.addElement("simple");
    defaultComboBoxModel1.addElement("graph-color-1");
    defaultComboBoxModel1.addElement("graph-color-2");
    defaultComboBoxModel1.addElement("rand-50-4-color1");
    defaultComboBoxModel1.addElement("rand-100-4-color1");
    defaultComboBoxModel1.addElement("rand-100-6-color1");
    defaultComboBoxModel1.addElement("spiral-500-4-color1");
    sampleComboBox.setModel(defaultComboBoxModel1);
    panel2.add(sampleComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2,
                                                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                                                                                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                                null, null, null, 0, false));
    loadButton = new AIButton();
    loadButton.setText("Run");
    panel2.add(loadButton, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 2,
                                                                            com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                            com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                            com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                            | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                            com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                            null, null, null, 0, false));
    resetButton = new AIButton();
    resetButton.setText("Stop");
    panel2.add(resetButton, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 2,
                                                                             com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                             com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                             com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                             | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                             com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                             null, null, null, 0, false));
    final AIPanel aIPanel1 = new AIPanel();
    panel2.add(aIPanel1, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 2,
                                                                          com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                          com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                                          1, 1, null, new Dimension(-1, 10),
                                                                          new Dimension(-1, 10), 0, false));
    stepSlider = new AISlider();
    stepSlider.setMajorTickSpacing(100);
    stepSlider.setMaximum(500);
    stepSlider.setMinimum(1);
    stepSlider.setMinorTickSpacing(50);
    stepSlider.setPaintLabels(false);
    stepSlider.setPaintTrack(true);
    stepSlider.setSnapToTicks(true);
    stepSlider.setValueIsAdjusting(true);
    panel2.add(stepSlider, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 2,
                                                                            com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                                                                            com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                            1,
                                                                            com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                            null, null, null, 0, false));
    labelsCheckbox = new AICheckBox();
    labelsCheckbox.setSelected(false);
    labelsCheckbox.setText("Labels");
    panel2.add(labelsCheckbox, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 2,
                                                                                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                                1,
                                                                                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                                null, null, null, 0, false));
    stepCheckBox = new AICheckBox();
    stepCheckBox.setText("Should step");
    panel2.add(stepCheckBox, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2,
                                                                              com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                              com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                                              com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                              | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                              com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                              | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                              null, null, null, 0, false));
    final JScrollPane scrollPane1 = new JScrollPane();
    panel2.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 2, 2,
                                                                             com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                             com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                                             com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                             | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                                                                             com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                             | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                                                                             null, null, null, 0, false));
    inputField = new AITextArea();
    inputField.setWrapStyleWord(false);
    scrollPane1.setViewportView(inputField);
    kField = new AITextField();
    kField.setText("4");
    panel2.add(kField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1,
                                                                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                        | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                        null, null, null, 0, false));
    final AILabel aILabel1 = new AILabel();
    aILabel1.setText("K:");
    panel2.add(aILabel1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1,
                                                                          com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                                                                          com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                                                                          com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                          com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                          null, null, null, 0, false));
    readFromFileButton = new AIButton();
    readFromFileButton.setText("From File");
    panel2.add(readFromFileButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2,
                                                                                    com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                                    com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                                                                                    com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                                                                                    com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                                                                                    null, null, null, 0, false));
    final AISplitPane aISplitPane1 = new AISplitPane();
    aISplitPane1.setBackground(new Color(-12516858));
    aISplitPane1.setContinuousLayout(true);
    aISplitPane1.setOneTouchExpandable(false);
    aISplitPane1.setOrientation(0);
    aISplitPane1.setResizeWeight(0.8);
    mainPanel.add(aISplitPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                                                                                 com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                                                                                 com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                                                                                 com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                                 | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                                 com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                                                                 | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                                                                                 null, new Dimension(500, 600), null, 0,
                                                                                 false));
    log = new AIContiniousScrollPane();
    aISplitPane1.setRightComponent(log);
    drawingCanvas = new AIGraphCanvas();
    aISplitPane1.setLeftComponent(drawingCanvas);
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainPanel;
  }
}