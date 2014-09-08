package ai.models;

/**
 * Created by Patrick on 08.09.2014.
 */
public class AIAdapter {

  private int width;
  private int height;
  protected AIAdapterListener listener;


  public void setListener(AIAdapterListener listener) {
    this.listener = listener;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
