package puzzles.twentyfortyeight;

/**
 * Created by Patrick on 29.10.2014.
 */
public interface GameButtonListener {

  void stepClicked();

  void stepChanged(int value);

  void resetClicked();

  void runClicked();

  void rightClicked();

  void downClicked();

  void leftClicked();

  void upClicked();
}
