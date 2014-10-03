package ai.gui;

import java.awt.*;

import ai.Log;

/**
 * Created by Patrick on 03.10.2014.
 */
public class ColorUtils {

  private static final String TAG = ColorUtils.class.getSimpleName();

  public static Color toHsv(double i, double brightness) {
    float hue = (float) ((i / 100.) * 360.);
    float value = (float) (1f * brightness);
    float sat = 0.45f;
    Log.v(TAG, "H: " + hue + ", S: " + sat + ", V: " + value);

    return Color.getHSBColor(hue, sat, value);
  }
}
