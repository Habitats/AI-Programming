package puzzles.graph_coloring.interfaces;

/**
 * Created by Patrick on 24.08.2014.
 */
public interface GraphColoringButtonListener {

  void resetClicked();

  void loadClicked();

  void stepClicked();

  void stepChanged(int value);
}
