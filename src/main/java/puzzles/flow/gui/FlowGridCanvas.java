package puzzles.flow.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

import ai.gui.AIGridCanvas;
import puzzles.flow.FlowTile;
import puzzles.shortestpath.gui.ShortestPathGui;

/**
 * Created by Patrick on 30.10.2014.
 */
public class FlowGridCanvas extends AIGridCanvas<FlowTile> {

  @Override
  protected void paintTile(Graphics g, FlowTile colorTile) {
    // put origin to be the left bottom corner
    int x = colorTile.x * getTileWidth();
    int y = getHeight() - getTileHeight() - colorTile.y * getTileHeight();

    drawTile(g, colorTile, x, y);

    if (drawLabels) {
      drawStringCenter(g, colorTile.getDomainText(), x, y);
    }

    if (ShortestPathGui.DRAW_OUTLINES) {
      drawOutline(g, x, y);
    }
    if (colorTile.getDirection() != null) {
      drawArrow(g, x, y, colorTile.getDirection());
    }
  }

  private void drawArrow(Graphics g, int x, int y, Direction direction) {
    Point start = new Point();
    Point end = new Point();
    int arrowLength = 40;
    switch (direction) {
      case UP:
        start.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) + (arrowLength / 2));
        end.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) - (arrowLength / 2));
        break;
      case RIGHT:
        start.setLocation(x + (getTileWidth() / 2) - (arrowLength / 2), y + (getTileHeight() / 2));
        end.setLocation(x + (getTileWidth() / 2) + (arrowLength / 2), y + (getTileHeight() / 2));
        break;
      case DOWN:
        start.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) - (arrowLength / 2));
        end.setLocation(x + (getTileWidth() / 2), y + (getTileHeight() / 2) + (arrowLength / 2));
        break;
      case LEFT:
        start.setLocation(x + (getTileWidth() / 2) + (arrowLength / 2), y + (getTileHeight() / 2));
        end.setLocation(x + (getTileWidth() / 2) - (arrowLength / 2), y + (getTileHeight() / 2));
        break;
    }
    createArrowShape((Graphics2D) g, start, end);
  }

  public static void createArrowShape(Graphics2D g, Point fromPt, Point toPt) {
    Polygon arrowPolygon = new Polygon();
    arrowPolygon.addPoint(-6, 1);
    arrowPolygon.addPoint(3, 1);
    arrowPolygon.addPoint(3, 3);
    arrowPolygon.addPoint(6, 0);
    arrowPolygon.addPoint(3, -3);
    arrowPolygon.addPoint(3, -1);
    arrowPolygon.addPoint(-6, -1);

    Point midPoint = midpoint(fromPt, toPt);

    double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

    AffineTransform transform = new AffineTransform();
    transform.translate(midPoint.x, midPoint.y);
    double ptDistance = fromPt.distance(toPt);
    double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
    transform.scale(scale, scale);
    transform.rotate(rotate);

    Shape shape = transform.createTransformedShape(arrowPolygon);
    g.fill(shape);
  }

  private static Point midpoint(Point p1, Point p2) {
    return new Point((int) ((p1.x + p2.x) / 2.0), (int) ((p1.y + p2.y) / 2.0));
  }

}
