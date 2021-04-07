package weave.ui.common;

import org.openqa.selenium.WebDriver;
import weave.ui.browsers.*;

public final class DriverFactory {

   public static final synchronized WebDriver getDriver(String driverType) {
      Browser browser;
      switch (driverType.toLowerCase()) {
         case "chrome":
            browser = new Chrome();
            break;
         case "chromeheadless":
            browser = new ChromeHeadless();
            break;
         case "firefox":
            browser = new Firefox();
            break;
         case "firefoxheadless":
            browser = new FirefoxHeadless();
            break;
         case "safari":
            browser = new Safari();
            break;
         default:
            throw new IllegalArgumentException(String.format("'%s' is invalid. Please enter a valid driverType.", driverType));
      }
      return browser.prepareLocalDriver();
   }
}
