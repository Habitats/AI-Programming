package a_star;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Astar implements Runnable {

  public static void main(String args[]) {
    new Astar().run();
  }

  @Override
  public void run() {
    BoardGridBag board = new BoardGridBag();
    System.out.println("yo");
  }
}
