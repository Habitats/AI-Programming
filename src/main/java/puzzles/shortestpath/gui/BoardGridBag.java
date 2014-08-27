package puzzles.shortestpath.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import puzzles.shortestpath.interfaces.ShortestPathButtonListener;
import ai.Log;
import puzzles.shortestpath.Samples;
import ai.models.Board;
import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AIComboBox;
import ai.gui.AITextArea;
import ai.gui.AITextField;

/**
 * Created by Patrick on 23.08.2014.
 */
public class BoardGridBag {

  private static final String TAG = BoardGridBag.class.getSimpleName();
  private AIButton astarButton;
  private AIButton resetButton;
  private AIButton BFSButton;
  private AIButton DFSButton;
  private AIButton loadButton;
  private AIButton stepButton;

  private JPanel mainPanel;
  private JFrame frame;

  private AICanvas drawingCanvas;
  private AITextArea inputField;
  private AITextField logField;
  private AITextField statusField;
  private AIComboBox comboBox1;

  private ShortestPathButtonListener listener;

  private void buildFrame() {
//    JFrame.setDefaultLookAndFeelDecorated(true);
//    try {
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    } catch (Exception e) {
//      Log.v(TAG, "invalid look and feel");
//    }
    frame = new JFrame();
    mainPanel.setPreferredSize(new Dimension(500, 300));
    inputField.setPreferredSize(new Dimension(100, 0));
    inputField.setLineWrap(true);
    Log.setLogField(logField);
    Log.setStatusField(statusField);
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(frame.getRootPane());
    frame.setVisible(true);
  }

  public BoardGridBag() {
    astarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.astarClicked();
      }
    });
    resetButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.resetClicked();
      }
    });
    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.loadClicked();
      }
    });
    BFSButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.bfsClicked();
      }
    });
    DFSButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.dfsClicked();
      }
    });
    buildFrame();
    comboBox1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        int i = cb.getSelectedIndex();
        inputField.setText(Samples.getAstarSample(i));
      }
    });
    stepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.stepClicked();
      }
    });
  }

  public void setAdapter(Board adapter) {
    drawingCanvas.setAdapter(adapter);
    frame.repaint();
  }

  public String getInput() {
    return inputField.getText().trim();
  }

  public void setListener(ShortestPathButtonListener listener) {
    this.listener = listener;
  }
}