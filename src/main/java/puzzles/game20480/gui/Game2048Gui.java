package puzzles.game20480.gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.Log;
import ai.gui.AIButton;
import ai.gui.AICanvas;
import ai.gui.AICheckBox;
import ai.gui.AIContiniousScrollPane;
import ai.gui.AIGridCanvas;
import ai.gui.AISlider;
import ai.gui.AITextField;
import ai.models.grid.Board;
import algorithms.a_star.AStar;
import puzzles.game20480.Game2048ButtonListener;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Game2048Gui {

  private static final String TAG = Game2048Gui.class.getSimpleName();
  private Game2048ButtonListener listener;
  private AIButton resetButton;
  private AIButton runButton;
  private AIButton stepButton;

  private JPanel mainPanel;

  private AIGridCanvas drawingCanvas;
  private AITextField statusField;
  private AITextField logField;
  private AISlider stepSlider;
  private AICheckBox labelsCheckbox;
  private AIContiniousScrollPane log;
  private AICheckBox stepCheckBox;
  private AIButton upButton;
  private AIButton downButton;
  private AIButton leftButton;
  private AIButton rightButton;


  public Game2048Gui(Game2048ButtonListener listener) {

    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
      if (e.getID() == KeyEvent.KEY_PRESSED) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          listener.upClicked();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          listener.downClicked();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          listener.rightClicked();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          listener.leftClicked();
        }
      }
      e.consume();
      return false;
    });

    buildFrame(getMainPanel(), log, statusField);
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
    rightButton.addActionListener(e -> listener.rightClicked());
    downButton.addActionListener(e -> listener.downClicked());
    leftButton.addActionListener(e -> listener.leftClicked());
    upButton.addActionListener(e -> listener.upClicked());
    leftButton.addKeyListener(new KeyAdapter() {
    });

    getDrawingCanvas().drawLabels(labelsCheckbox.isSelected());

  }

  protected void buildFrame(JPanel mainPanel, AIContiniousScrollPane log, AITextField statusField) {
    JFrame frame = new JFrame();
    mainPanel.setPreferredSize(getPreferredSize());
    Log.setLogField(log);
    Log.setStatusField(statusField);
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(getDefaultCloseOperation());
    frame.pack();
    frame.setLocationRelativeTo(frame.getRootPane());
    frame.setVisible(true);
  }

  public void setAdapter(Board board) {
    getDrawingCanvas().setAdapter(board);
    Log.v(TAG, "setting setAdapter!" + board);
    getMainPanel().repaint();
  }

  protected int getDefaultCloseOperation() {
    return WindowConstants.DISPOSE_ON_CLOSE;
  }

  protected Dimension getPreferredSize() {
    return new Dimension(900, 700);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public AICanvas getDrawingCanvas() {
    return drawingCanvas;
  }
}