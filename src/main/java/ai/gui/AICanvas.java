package ai.gui;

import java.awt.*;

import javax.swing.*;

import ai.models.AIAdapter;
import ai.models.AIAdapterListener;

/**
 * Created by Patrick on 24.08.2014.
 */
public abstract class AICanvas<T> extends JPanel implements AIAdapterListener {

  private static final String TAG = AICanvas.class.getSimpleName();
  private AIAdapter adapter;
  public boolean drawLabels;

  public AICanvas() {
    super();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    if (getAdapter() != null) {
      updateMetrics();
      draw((Graphics2D) g);
    }
  }

  protected abstract void draw(Graphics2D g);

  protected abstract void drawOutline(Graphics g, int x, int y);

  protected void drawStringCenter(Graphics g, String s, int XPos, int YPos) {
    Graphics2D g2d = (Graphics2D) g;
    g.setColor(Theme.getForeground());
    int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
    int stringHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
    int offsetWidth = getItemWidth() / 2 - stringLen / 2;
    int offsetHeight = getItemHeight() - stringHeight / 2;
    g2d.drawString(s, offsetWidth + XPos, offsetHeight + YPos);
  }

  protected abstract int getItemHeight();

  protected abstract int getItemWidth();

  protected abstract void updateMetrics();

  @Override
  public void notifyDataChanged() {
    repaint();
  }

  public void setAdapter(AIAdapter<T> adapter) {
    this.adapter = adapter;
    adapter.setListener(this);
  }

  public void drawLabels(boolean selected) {
    this.drawLabels = selected;
    repaint();
  }

  public AIAdapter<T> getAdapter() {
    return adapter;
  }
}
