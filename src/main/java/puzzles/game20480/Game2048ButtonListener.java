package puzzles.game20480;

/**
 * Created by Patrick on 29.10.2014.
 */
public interface Game2048ButtonListener {

  void stepClicked();

  void stepChanged(int value);

  void resetClicked();

  void runClicked();

  void rightClicked();

  void downClicked();

  void leftClicked();

  void upClicked();
}
