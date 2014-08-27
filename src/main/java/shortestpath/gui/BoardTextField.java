package shortestpath.gui;

import javax.swing.*;

/**
 * Created by Patrick on 27.08.2014.
 */
public class BoardTextField extends JTextField {

  public BoardTextField() {
    setBackground(Theme.getBackgroundInteractive());
    setForeground(Theme.getForeground());
    setBorder(BorderFactory.createEmptyBorder());
  }
}
