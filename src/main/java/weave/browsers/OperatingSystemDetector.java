package weave.browsers;

public class OperatingSystemDetector {

   private static final String CURRENT_OS;

   static {
      CURRENT_OS = System.getProperty("os.name").toLowerCase();
   }

   public static boolean isMac() {
      return CURRENT_OS.indexOf("mac") >= 0;
   }

   public static boolean isUnix() {
      return
         CURRENT_OS.indexOf("nix") >= 0 ||
            CURRENT_OS.indexOf("aix") >= 0 ||
            CURRENT_OS.indexOf("nux") >= 0
         ;
   }
}
