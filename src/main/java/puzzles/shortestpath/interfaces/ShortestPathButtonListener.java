package puzzles.shortestpath.interfaces;

/**
 * Created by Patrick on 24.08.2014.
 */
public interface ShortestPathButtonListener {

  void astarClicked();

  void resetClicked();

  void loadClicked();

  void dfsClicked();

  void bfsClicked();

  void stepClicked();

  void stepChanged(int value);
}
