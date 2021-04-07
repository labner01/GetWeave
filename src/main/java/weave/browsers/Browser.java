package weave.browsers;

import org.openqa.selenium.WebDriver;
import weave.common.EnvManager;

public interface Browser {
   String DRIVER_LOCATION = "src/test/resources/drivers/";
   String MAC_CHROME_DRIVER_NAME = "chromedriver_mac";
   String MAC_FIREFOX_DRIVER_NAME = "geckodriver_mac";
   String LNX_CHROME_DRIVER_NAME = "chromedriver_linux";
   String LNX_FIREFOX_DRIVER_NAME = "geckodriver_linux";

   WebDriver prepareLocalDriver();

   default String getDriverPath() {
      EnvManager envManager = new EnvManager();
      String driverName = "";
      if (OperatingSystemDetector.isMac()) {
         if (envManager.getBrowserType().toLowerCase().contains("chrome")) {
            driverName = MAC_CHROME_DRIVER_NAME;
         } else if (envManager.getBrowserType().toLowerCase().contains("firefox")) {
            driverName = MAC_FIREFOX_DRIVER_NAME;
         }
      } else if (OperatingSystemDetector.isUnix()) {
         if (envManager.getBrowserType().toLowerCase().contains("chrome")) {
            driverName = LNX_CHROME_DRIVER_NAME;
         } else if (envManager.getBrowserType().toLowerCase().contains("firefox")) {
            driverName = LNX_FIREFOX_DRIVER_NAME;
         }
      } else {
         throw new IllegalArgumentException("Only Mac an Linux drivers are supported.");
      }
      return DRIVER_LOCATION + driverName;
   }
}
