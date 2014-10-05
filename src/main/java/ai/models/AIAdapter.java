package ai.models;

import java.util.Collection;

import algorithms.csp.canonical_utils.VariableListener;

/**
 * Created by Patrick on 08.09.2014.
 */
public abstract class AIAdapter<T extends Node & VariableListener> {

  private int width;
  private int height;
  private AIAdapterListener listener;
  private int minX;
  private int minY;


  public void setListener(AIAdapterListener listener) {
    this.listener = listener;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void notifyDataChanged() {
    if (listener != null) {
      listener.notifyDataChanged();
    }
  }

  public abstract T getItem(int index);

  public void setWidth(int width) {
    this.width = width;
  }

  public abstract int getSize();

  public abstract Collection<T> getItems();

  public void setHeight(int height) {
    this.height = height;
  }

  public void setOrigin(int minX, int minY) {
    this.minX = minX;
    this.minY = minY;
  }

  public int getOriginX() {
    return minX;
  }

  public int getOriginY() {
    return minY;
  }

  public abstract boolean isLegalPosition(T tile);
}
