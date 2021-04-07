package weave.ui.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Utils {

   public void sleep(int millisecond) {
      try {
         Thread.sleep(millisecond);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public String getCurrentTimeStamp() {
      return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
   }
}
