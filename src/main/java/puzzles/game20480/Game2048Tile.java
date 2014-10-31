package puzzles.game20480;

import ai.models.grid.ColorTile;

/**
 * Created by Patrick on 30.10.2014.
 */
public class Game2048Tile extends ColorTile<Game2048Tile> {

  private VALUE v;
  private boolean combined;

  public Game2048Tile(int x, int y) {
    super(x, y, VALUE.values().length);
    setValue(VALUE.v0);
  }

  public void setValue(VALUE value) {
    this.v = value;
    setColor(v.ordinal());
    setDescription(v.name());
  }

  public boolean isCombined() {
    return combined;
  }

  public void setCombined(boolean combined) {
    this.combined = combined;
  }

  public void reset() {
    combined = false;
  }

  public void setEmpty() {
    setValue(VALUE.v0);
  }


  public VALUE getValue() {
    return v;
  }

  @Override
  public String getId() {
    return super.getId() + "-" + v.name();
  }

  public void incremenet() {
    boolean hit = false;
    for (VALUE value : VALUE.values()) {
      if (v == value && !hit) {
        hit = true;
      } else if (hit) {
        setValue(value);
        break;
      }
    }
  }

  public enum VALUE {
    v0(0), v2(2), v4(4), v8(8), v16(16), v32(32), v64(64), v128(128), v256(256), v512(512), v1024(1024), v2048(
        2048), v4096(4096), v8192(8192);
    public final int VAL;

    VALUE(int i) {
      this.VAL = i;
    }
  }

  @Override
  public void update(Game2048Tile colorTile) {
    super.update(colorTile);
    setValue(colorTile.v);
    combined = colorTile.combined;
  }

  @Override
  public boolean isEmpty() {
    return v == VALUE.v0;
  }
}
