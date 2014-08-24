package aiprog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Patrick on 23.08.2014.
 */
public class Log {

  private static String getPrettyDate() {
    DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss:SS");
    return format.print(new DateTime());
  }

  public static void v(String tag, String msg, Exception e) {
    System.out.printf("%s > %s > %s", getPrettyDate(), tag, msg);
  }

  public static void v(String tag, String msg) {
    System.out.printf("%s > %s > %s", getPrettyDate(), tag, msg);
  }
}
