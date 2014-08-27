package aiprog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Log {

  private static JTextField logField;
  private static JTextField statusField;

  private static String getPrettyDate() {
    DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss:SS");
    return format.print(new DateTime());
  }

  public static void v(String tag, String msg, Exception e) {
    log(String.format("%s > %s > %s", getPrettyDate(), tag, msg));
  }

  public static void v(String tag, Object msg) {
    log(String.format("%s > %s > %s", getPrettyDate(), tag, msg.toString()));
  }

  private static void log(String msg) {
    System.out.println(msg);
    if (logField != null) {
      logField.setText(msg);
    }
  }

  private static void status(String tag, String status) {
    System.out.println(status);
    if (statusField != null) {
      statusField.setText(status);
    }
  }

  public static void s(String tag, String status) {
    status(tag, status);
  }

  public static void setStatusField(JTextField statusField) {
    Log.statusField = statusField;
  }

  public static void setLogField(JTextField statusField) {
    Log.logField = statusField;
  }
}
