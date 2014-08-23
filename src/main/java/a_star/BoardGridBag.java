package a_star;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created by Patrick on 23.08.2014.
 */
public class BoardGridBag {

  private static final String TAG = BoardGridBag.class.getSimpleName();
  private JButton runButton;
  private JButton resetButton;
  private JTextArea inputField;
  private JPanel drawingCanvas;
  private JPanel mainPanel;

  private void buildFrame() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      Log.v(TAG, "invalid look and feel");
    }
    JFrame frame = new JFrame();
    mainPanel.setPreferredSize(new Dimension(500, 300));
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    Log.v(TAG, "yoloswagger");
    frame.pack();
    frame.setLocationRelativeTo(frame.getRootPane());
    frame.setVisible(true);
  }

  public BoardGridBag() {
    runButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
    resetButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });

    buildFrame();
  }
}
