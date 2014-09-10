package ai.gui;

import java.awt.*;

import javax.swing.*;

import ai.Log;

/**
 * Created by Patrick on 08.09.2014.
 */
public class AIGui {

  private JFrame frame;

  protected void buildFrame(JPanel mainPanel, AIContiniousScrollPane log, AITextField statusField) {
//    JFrame.setDefaultLookAndFeelDecorated(true);
//    try {
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    } catch (Exception e) {
//      Log.v(TAG, "invalid look and feel");
//    }
    frame = new JFrame();
    mainPanel.setPreferredSize(new Dimension(700, 500));
    Log.setLogField(log);
    Log.setStatusField(statusField);
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(frame.getRootPane());
    frame.setVisible(true);
  }

  public JFrame getFrame() {
    return frame;
  }
}
