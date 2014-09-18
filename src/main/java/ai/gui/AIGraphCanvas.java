package ai.gui;

import java.awt.*;
import java.util.List;

import ai.models.graph.ColorNode;

/**
 * Created by Patrick on 08.09.2014.
 */
public class AIGraphCanvas extends AICanvas<ColorNode> {

  private static final String TAG = AIGraphCanvas.class.getSimpleName();
  private int itemHeight = 30;
  private int itemWidth = 30;
  private double horizontalScalingFactor;
  private double verticalScalingFactor;
  private int padding = 30;


  @Override
  protected void draw(Graphics2D g) {
    drawEdges(g);
    drawNodes(g);

//    int border = 10;
//    g.drawRect(border, border, getWidth() - border * 2, getHeight() - border * 2);
  }

  private void drawEdges(Graphics2D g) {
    for (ColorNode item : getAdapter().getItems()) {
      int startX = getCenterX(item);
      int startY = getCenterY(item);

      List<ColorNode> children = item.getChildren();
      for (ColorNode child : children) {
        int endX = getCenterX(child);
        int endY = getCenterY(child);

        g.setColor(Color.white);
        g.drawLine(startX, startY, endX, endY);
      }
    }
  }


  private void drawNodes(Graphics2D g) {
    for (ColorNode item : getAdapter().getItems()) {
      int x = getX(item);
      int y = getY(item);
      g.setColor(item.getColor());
      g.fillOval(x, y, getItemWidth(), getItemHeight());
      g.setColor(Color.black);
      g.drawOval(x, y, getItemWidth(), getItemHeight());

      drawStringCenter(g,item.getId(),x,y);
      drawStringCenter(g,item.getDescription(),x,y+20);
    }
  }

  private int getY(ColorNode item) {
    return (int) (getHeight() - item.getY() * getVerticalScalingFactor()) - (padding + itemHeight);
  }

  private int getX(ColorNode item) {
    return (int) (item.getX() * getHorizontalScalingFactor()) + padding;
  }

  private int getCenterY(ColorNode item) {
    return getY(item) + getItemHeight() / 2;
  }

  private int getCenterX(ColorNode item) {
    return getX(item) + getItemWidth() / 2;
  }



  @Override
  protected void drawOutline(Graphics g, int x, int y) {

  }

  @Override
  protected int getItemHeight() {
    return itemHeight;
  }

  @Override
  protected int getItemWidth() {
    return itemWidth;
  }

  @Override
  protected void updateMetrics() {
    setVerticalScalingFactor(getHeight(), getAdapter().getHeight());
    setHorizontalScalingFactor(getWidth(), getAdapter().getWidth());
  }

  public void setHorizontalScalingFactor(double width, double adapterWidth) {
    this.horizontalScalingFactor = (width - (padding * 2 + itemWidth)) / adapterWidth;
  }

  public double getHorizontalScalingFactor() {
    return horizontalScalingFactor;
  }

  public void setVerticalScalingFactor(double height, double adapterHeight) {
    this.verticalScalingFactor = (height - (padding * 2 + itemHeight)) / adapterHeight;
  }

  public double getVerticalScalingFactor() {
    return verticalScalingFactor;
  }
}
