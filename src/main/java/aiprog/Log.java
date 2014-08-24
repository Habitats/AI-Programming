package aiprog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Log {

  private static JTextField statusField;

  private static String getPrettyDate() {
    DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss:SS");
    return format.print(new DateTime());
  }

  public static void v(String tag, String msg, Exception e) {
    log(String.format("%s > %s > %s", getPrettyDate(), tag, msg));
  }

  public static void v(String tag, String msg) {
    log(String.format("%s > %s > %s", getPrettyDate(), tag, msg));
  }

  private static void log(String msg) {
    System.out.println(msg);
    if(statusField != null) {
      statusField.setText(msg);
    }
  }

  public static void setStatusField(JTextField statusField) {
    Log.statusField = statusField;
  }
}
