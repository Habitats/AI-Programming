package shortestpath.gui;

import javax.swing.*;

/**
 * Created by Patrick on 27.08.2014.
 */
public class BoardComboBox<E> extends JComboBox<E> {

  public BoardComboBox() {
    super();
    setForeground(Theme.getForeground());
    setBackground(Theme.getBackgroundInteractive());
  }
}
