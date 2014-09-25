package ai.gui;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

import ai.Log;

/**
 * Created by Patrick on 08.09.2014.
 */
public abstract class AIGui {

  private JFrame frame;

  protected void buildFrame(JPanel mainPanel, AIContiniousScrollPane log, AITextField statusField) {
//    JFrame.setDefaultLookAndFeelDecorated(true);
//    try {
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    } catch (Exception e) {
//      Log.v(TAG, "invalid look and feel");
//    }
    frame = new JFrame();
    mainPanel.setPreferredSize(getPreferredSize());
    Log.setLogField(log);
    Log.setStatusField(statusField);
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(getDefaultCloseOperation());
    frame.pack();
    frame.setLocationRelativeTo(frame.getRootPane());
    frame.setVisible(true);
  }

  protected abstract int getDefaultCloseOperation();

  protected abstract Dimension getPreferredSize();

  protected static String readFile(String path, Charset encoding) {
    byte[] encoded = new byte[0];
    try {
      encoded = Files.readAllBytes(Paths.get(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String(encoded, encoding);
  }

  public JFrame getFrame() {
    return frame;
  }

protected void init() {
  }
}
