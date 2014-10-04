package ai.gui;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

import ai.Log;
import ai.models.AIAdapter;
import ai.models.Node;
import algorithms.csp.CspButtonListener;
import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public abstract class AIGui<T extends Node & VariableListener> {

  private static final String TAG = AIGui.class.getSimpleName();
  protected CspButtonListener listener;
  private JFrame frame;

  protected void buildFrame(JPanel mainPanel, AIContiniousScrollPane log, AITextField statusField) {

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

  public void setAdapter(AIAdapter<T> adapter) {

//    setAdapter.setOrigin(minX, minY);

    getDrawingCanvas().setAdapter(adapter);
    Log.v(TAG, "setting setAdapter!" + adapter);
    getMainPanel().repaint();
  }

  public String getInput() {
    return getInputField().getText().trim();
  }

  public void setListener(CspButtonListener listener) {
    this.listener = listener;
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

  public abstract JPanel getMainPanel();

  public abstract AICanvas getDrawingCanvas();

  public abstract AITextArea getInputField();
}
