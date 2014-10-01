package algorithms.csp;

/**
 * Created by Patrick on 24.08.2014.
 */
public interface CspButtonListener {

  void resetClicked();

  void loadClicked();

  void stepClicked();

  void stepChanged(int value);

  void sampleSelected(int i);
}
