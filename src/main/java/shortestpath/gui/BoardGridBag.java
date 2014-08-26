package shortestpath.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import aiprog.Log;
import shortestpath.Samples;
import shortestpath.interfaces.AstarButtonListener;
import shortestpath.models.Board;

/**
 * Created by Patrick on 23.08.2014.
 */
public class BoardGridBag {

  private static final String TAG = BoardGridBag.class.getSimpleName();
  private JButton runButton;
  private JButton resetButton;
  private JTextArea inputField;
  private BoardCanvas drawingCanvas;
  private JPanel mainPanel;
  private JButton loadButton;
  private JTextField statusField;
  private JButton BFSButton;
  private JButton DFSButton;
  private JComboBox comboBox1;
  private AstarButtonListener listener;
  private JFrame frame;

  private void buildFrame() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      Log.v(TAG, "invalid look and feel");
    }
    frame = new JFrame();
    mainPanel.setPreferredSize(new Dimension(500, 300));
    inputField.setPreferredSize(new Dimension(100, 0));
    inputField.setLineWrap(true);
    Log.setStatusField(statusField);
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(frame.getRootPane());
    frame.setVisible(true);
  }

  public BoardGridBag() {
    runButton.addActionListener(new ActionListener() {
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
  }

  public void setAdapter(Board adapter) {
    drawingCanvas.setAdapter(adapter);
    frame.repaint();
  }

  public String getInput() {
    return inputField.getText().trim();
  }

  public void setListener(AstarButtonListener listener) {
    this.listener = listener;
  }
}