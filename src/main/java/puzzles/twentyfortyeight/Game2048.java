package puzzles.twentyfortyeight;

import puzzles.twentyfortyeight.gui.Game2048Gui;

/**
 * Created by Patrick on 30.10.2014.
 */
public class Game2048 implements Runnable, GameButtonListener {

  @Override
  public void run() {
    Game2048Gui gui = new Game2048Gui(this);
  }

  @Override
  public void stepClicked() {

  }

  @Override
  public void stepChanged(int value) {

  }

  @Override
  public void resetClicked() {

  }

  @Override
  public void runClicked() {

  }

  @Override
  public void rightClicked() {

  }

  @Override
  public void downClicked() {

  }

  @Override
  public void leftClicked() {

  }

  @Override
  public void upClicked() {

  }
}
